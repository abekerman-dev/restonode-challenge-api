package com.truenorth.restonode.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.truenorth.restonode.distanceclient.DistanceMatrixClient;
import com.truenorth.restonode.dto.CustomerNotificationMessage;
import com.truenorth.restonode.dto.RestaurantOrderMessage;
import com.truenorth.restonode.exception.InvalidOrderException;
import com.truenorth.restonode.exception.InvalidRatingException;
import com.truenorth.restonode.exception.ResourceNotFoundException;
import com.truenorth.restonode.exception.UnfinishedTransactionException;
import com.truenorth.restonode.messaging.RabbitMQSender;
import com.truenorth.restonode.model.DeliveryOrder;
import com.truenorth.restonode.model.Meal;
import com.truenorth.restonode.model.Rating;
import com.truenorth.restonode.model.Restaurant;
import com.truenorth.restonode.repository.DeliveryOrderRepository;
import com.truenorth.restonode.repository.MealRepository;
import com.truenorth.restonode.repository.RestaurantRepository;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Data
@Slf4j
public class RestonodeServiceImpl implements RestonodeService {

	@Autowired
	private RestaurantRepository restaurantRepo;

	@Autowired
	private DeliveryOrderRepository orderRepo;

	@Autowired
	private MealRepository mealRepo;

	@Autowired
	@Qualifier("notificationSender")
	private RabbitMQSender notificationSender;

	@Autowired
	@Qualifier("orderSender")
	private RabbitMQSender orderSender;

	@Autowired
//	@Qualifier("mockDistanceMatrixClient")
	@Qualifier("googleMapsDistanceMatrixClient")
	private DistanceMatrixClient distanceMatrixClient;

	@Override
	public Restaurant addRestaurantRating(Long restaurantId, Rating rating)
			throws ResourceNotFoundException, InvalidRatingException {
		if (rating.getRating() < 1 || rating.getRating() > 5) {
			throw new InvalidRatingException();
		}
		final Restaurant restaurant = getRestaurantById(restaurantId);
		restaurant.addRating(rating);

		return restaurantRepo.save(restaurant);
	}

	@Override
	public List<Restaurant> getRestaurants(Optional<Integer> rating) {
		return rating.isPresent() ? restaurantRepo.findByRating(rating.get()) : restaurantRepo.findAll();
	}

	@Override
	public CustomerNotificationMessage createDeliveryOrder(DeliveryOrder order) throws UnfinishedTransactionException {
		try {
			final List<Meal> meals = order.getMeals();

			validateOrder(meals);

			order.setEta(distanceMatrixClient.calculateETA(order.getRestaurant()
					.getLocation(), order.getDestination()));
			order.setTotalAmount(calculateOrderTotalAmount(order));
			order.setTimestamp(LocalDateTime.now());
			
			DeliveryOrder newOrder = orderRepo.save(order);
			log.debug("Created:" + newOrder);
			
			CustomerNotificationMessage notificationMessage = CustomerNotificationMessage.fromOrder(newOrder);
			notificationSender.send(notificationMessage);
			orderSender.send(RestaurantOrderMessage.fromOrder(newOrder));

			return notificationMessage;
		} catch (Exception e) {
			log.error("INTERNAL SERVER ERROR", e);
			throw new UnfinishedTransactionException(e);
		}
	}

	private void validateOrder(final List<Meal> meals) throws ResourceNotFoundException, InvalidOrderException {
		if (meals.isEmpty()) {
			throw new ResourceNotFoundException(Meal.class, meals.stream()
					.map(Meal::getId)
					.collect(Collectors.toList()));
		}

		boolean allMealsSameRestaurant = meals.stream()
				.map(Meal::getRestaurant)
				.allMatch(meals.get(0)
						.getRestaurant()::equals);
		if (!allMealsSameRestaurant) {
			throw new InvalidOrderException();
		}
	}

	@Override
	public List<Meal> findMealsInIdList(List<Long> ids) {
		return mealRepo.findByIdList(ids);
	}

	@Override
	public List<DeliveryOrder> getOrders() {
		return orderRepo.findAll();
	}

	public Restaurant getRestaurantById(Long restaurantId) throws ResourceNotFoundException {
		return restaurantRepo.findById(restaurantId)
				.orElseThrow(() -> new ResourceNotFoundException(Restaurant.class, restaurantId));
	}

	@Override
	public Restaurant createRestaurant(Restaurant restaurant) {
		return restaurantRepo.save(restaurant);
	}

	@Override
	public Restaurant addRestaurantMeal(Long restaurantId, Meal meal) throws ResourceNotFoundException {
		final Restaurant restaurant = getRestaurantById(restaurantId);
		restaurant.addMeal(meal);

		return restaurantRepo.save(restaurant);
	}

	private BigDecimal calculateOrderTotalAmount(DeliveryOrder order) {
		return order.getMeals()
				.stream()
				.map(Meal::getPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

}

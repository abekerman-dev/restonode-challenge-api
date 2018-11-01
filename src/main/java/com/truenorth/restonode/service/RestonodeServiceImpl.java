package com.truenorth.restonode.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.maps.model.Duration;
import com.truenorth.restonode.distanceclient.DistanceMatrixClient;
import com.truenorth.restonode.exception.RestaurantNotFoundException;
import com.truenorth.restonode.messaging.RabbitMQSender;
import com.truenorth.restonode.model.DeliveryOrder;
import com.truenorth.restonode.model.Rating;
import com.truenorth.restonode.model.Restaurant;
import com.truenorth.restonode.repository.DeliveryOrderRepository;
import com.truenorth.restonode.repository.RestaurantRepository;

import lombok.Data;

@Service
@Data
public class RestonodeServiceImpl implements RestonodeService {

	@Autowired
	private RestaurantRepository restaurantRepo;

	@Autowired
	private DeliveryOrderRepository orderRepo;

	@Autowired
	@Qualifier("notificationSender")
	private RabbitMQSender notificationSender;
	
	@Autowired
	@Qualifier("orderSender")
	private RabbitMQSender orderSender;

	@Autowired
	@Qualifier("mockDistanceMatrixClient") // FIXME switch to real one
	private DistanceMatrixClient distanceMatrixClient;
	
	@Override
	public Restaurant addRestaurantRating(Long restaurantId, Rating rating) {
		final Restaurant restaurant = getRestaurantOrThrowException(restaurantId);
		restaurant.addRating(rating);

		return restaurantRepo.save(restaurant);
	}

	@Override
	public List<Restaurant> find(Optional<Integer> rating) {
		return rating.isPresent() ? restaurantRepo.findByRating(rating.get()) : restaurantRepo.findAll();
	}

	@Override
	public Duration createDeliveryOrder(DeliveryOrder order) throws Exception {
		Restaurant restaurant = getRestaurantOrThrowException(order.getRestaurant().getId());
		
		Duration duration = distanceMatrixClient.calculateDuration(restaurant.getLocation(), order.getDestination());
		notificationSender.send(duration);
		
		DeliveryOrder newOrder = orderRepo.save(order);
		// setting only email to avoid sending all other restaurant's properties (notably its ratings)
		newOrder.setRestaurant(new Restaurant(null, null, restaurant.getEmail()));
		orderSender.send(newOrder);
		
		return duration;
	}

	private Restaurant getRestaurantOrThrowException(Long restaurantId) {
		return restaurantRepo.findById(restaurantId).orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
	}

}

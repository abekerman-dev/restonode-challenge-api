package com.truenorth.restonode.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.maps.model.Duration;
import com.truenorth.restonode.distanceclient.DistanceMatrixClient;
import com.truenorth.restonode.exception.RestaurantNotFoundException;
import com.truenorth.restonode.messaging.RabbitMQSender;
import com.truenorth.restonode.model.DeliveryOrder;
import com.truenorth.restonode.model.Rating;
import com.truenorth.restonode.model.Restaurant;
import com.truenorth.restonode.model.RestonodeMessageType;
import com.truenorth.restonode.repository.DeliveryOrderRepository;
import com.truenorth.restonode.repository.RestaurantRepository;

@Service
public class RestonodeService {

	@Autowired
	private RestaurantRepository restaurantRepo;

	@Autowired
	private DeliveryOrderRepository orderRepo;

	@Autowired
	private RabbitMQSender rabbitMQsender;

	@Autowired
	private DistanceMatrixClient distanceMatrixClient;

	public Restaurant addRestaurantRating(Long restaurantId, Rating rating) {
		final Restaurant restaurant = getRestaurantOrThrowException(restaurantId);
		restaurant.addRating(rating);

		return restaurantRepo.save(restaurant);
	}

	public List<Restaurant> find(Optional<Integer> rating) {
		return rating.isPresent() ? restaurantRepo.findByRating(rating.get()) : restaurantRepo.findAll();
	}

	public Duration createDeliveryOrder(Long orderId, DeliveryOrder order) throws Exception {
		Restaurant restaurant = getRestaurantOrThrowException(orderId);
		orderRepo.save(order);
		rabbitMQsender.send(RestonodeMessageType.NOTIFICATION);
		rabbitMQsender.send(RestonodeMessageType.ORDER);

		return distanceMatrixClient.calculateDuration(restaurant.getLocation(), order.getDestination());
	}

	private Restaurant getRestaurantOrThrowException(Long restaurantId) {
		return restaurantRepo.findById(restaurantId).orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
	}

}

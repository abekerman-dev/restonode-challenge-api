package com.truenorth.restonode.service;

import java.util.List;
import java.util.Optional;

import com.google.maps.model.Duration;
import com.truenorth.restonode.model.DeliveryOrder;
import com.truenorth.restonode.model.Rating;
import com.truenorth.restonode.model.Restaurant;

public interface RestonodeService {

	Restaurant addRestaurantRating(Long restaurantId, Rating rating);

	List<Restaurant> find(Optional<Integer> rating);

	Duration createDeliveryOrder(DeliveryOrder order) throws Exception;

}
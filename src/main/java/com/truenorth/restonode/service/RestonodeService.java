package com.truenorth.restonode.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truenorth.restonode.exception.RestaurantNotFoundException;
import com.truenorth.restonode.model.Rating;
import com.truenorth.restonode.model.Restaurant;
import com.truenorth.restonode.repository.RestaurantRepository;

@Service
public class RestonodeService {

	@Autowired
	private RestaurantRepository repository;

	public Restaurant addRestaurantRating(Long restaurantId, Rating rating) {
		return repository.findById(restaurantId).map(restaurant -> {
			restaurant.addRating(rating);
			return repository.save(restaurant);
		}).orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
	}

	public List<Restaurant> find(Optional<Integer> rating) {
		return rating
			.isPresent() ? 
				repository.findByRating(rating.get()) : 
				repository.findAll();
	}
}

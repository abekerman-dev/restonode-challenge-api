package com.truenorth.restonode.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.truenorth.restonode.model.DeliveryOrder;
import com.truenorth.restonode.model.Rating;
import com.truenorth.restonode.model.Restaurant;
import com.truenorth.restonode.service.RestonodeService;

@RestController
public class RestaurantController {

	@Autowired
	private RestonodeService service;

	/**
	 * Endpoint to add a rating to an existing restaurant
	 * 
	 * @param rating
	 * @param id
	 * @return
	 */
	@PutMapping("/restaurants/{id}/rating")
	public Restaurant addRestaurantRating(@PathVariable Long id, @RequestBody Rating rating) {
		return service.addRestaurantRating(id, rating);
	}

	/**
	 * Endpoint to list all restaurants with the ability to filter by rating
	 * 
	 * @param rating Optional integer between 1 and 5, used to filter restaurants
	 *               with such rating
	 * @return
	 */
	@GetMapping("/restaurants")
	List<Restaurant> all(@RequestParam(value = "rating") Optional<Integer> rating) {
		return service.find(rating);
	}

	/**
	 * Endpoint to save the delivery order. RabbitMQ messages are triggered (one for
	 * the notification, another for the order)
	 * 
	 * @param id
	 * @param order
	 * @return ETA based on user and restaurant locations
	 */
	@PutMapping("/restaurants/{id}/order")
	Long processDeliveryOrder(@PathVariable Long id, @RequestBody DeliveryOrder order) {
		
		
		return null;
	}

}

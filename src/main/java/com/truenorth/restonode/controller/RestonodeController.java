package com.truenorth.restonode.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.truenorth.restonode.dto.CustomerNotificationMessage;
import com.truenorth.restonode.dto.DeliveryOrderRestRequestBody;
import com.truenorth.restonode.exception.InvalidOrderException;
import com.truenorth.restonode.exception.InvalidRatingException;
import com.truenorth.restonode.exception.ResourceNotFoundException;
import com.truenorth.restonode.exception.UnfinishedTransactionException;
import com.truenorth.restonode.model.DeliveryOrder;
import com.truenorth.restonode.model.Meal;
import com.truenorth.restonode.model.Rating;
import com.truenorth.restonode.model.Restaurant;
import com.truenorth.restonode.service.RestonodeService;

/**
 * Only controller in the whole REST API / SpringBoot app refer to this postman
 * collection to see examples of how to invoke the endpoints declared here:
 * https://www.getpostman.com/collections/480b4b5d3508b1d8a243
 * 
 * @author andres
 *
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RestonodeController {

	@Autowired
	private RestonodeService service;

	/**
	 * Endpoint to add a rating to an existing restaurant
	 * 
	 * @param rating
	 * @param id
	 * @return
	 * @throws InvalidRatingException
	 * @throws ResourceNotFoundException
	 */
	@PostMapping("/restaurants/{id}/rating")
	public Restaurant addRestaurantRating(@PathVariable Long id, @RequestBody Rating rating)
			throws InvalidRatingException, ResourceNotFoundException {

		if (rating.getRating() < 1 || rating.getRating() > 5) {
			throw new InvalidRatingException();
		}

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
	public List<Restaurant> getAllRestaurants(@RequestParam Optional<Integer> rating) {
		return service.getRestaurants(rating);
	}

	/**
	 * Endpoint to create a new DeliveryOrder. It first validates that all the input
	 * meal IDs belong to the same restaurant, then it calls the service to create
	 * the order
	 * 
	 * @param orderDto
	 * @return
	 * @throws InvalidOrderException          if not all meal IDs belong to the same
	 *                                        restaurant
	 * @throws ResourceNotFoundException      if no meals were found for the input
	 *                                        IDs
	 * @throws UnfinishedTransactionException if the service fails to finish the
	 *                                        creation transaction
	 */
	@PostMapping("/orders")
	public CustomerNotificationMessage createDeliveryOrder(@RequestBody DeliveryOrderRestRequestBody orderDto)
			throws InvalidOrderException, ResourceNotFoundException, UnfinishedTransactionException {

		final List<Meal> meals = service.findMealsInIdList(orderDto.getMealIds());
		if (meals.isEmpty()) {
			throw new ResourceNotFoundException(Meal.class, orderDto.getMealIds());
		}

		boolean allMealsSameRestaurant = meals.stream().map(Meal::getRestaurant)
				.allMatch(meals.get(0).getRestaurant()::equals);
		if (!allMealsSameRestaurant) {
			throw new InvalidOrderException();
		}

		return service.createDeliveryOrder(new DeliveryOrder(meals, orderDto.getAddress(), orderDto.getDestination()));
	}

	/**
	 * Endpoint to list all the orders
	 * 
	 * @return
	 */
	@GetMapping("/orders")
	public List<DeliveryOrder> getAllOrders() {
		return service.getOrders();
	}

	/**
	 * Endpoint to get a restaurant by id
	 * 
	 * @return
	 */
	@GetMapping("/restaurants/{id}")
	public Restaurant getRestaurantById(@PathVariable Long id) throws ResourceNotFoundException {
		return service.getRestaurantById(id);
	}

	/**
	 * Endpoint to create a restaurant
	 * 
	 * @param restaurant
	 * @return
	 */
	@PostMapping("/restaurants")
	public Restaurant createRestaurant(@RequestBody Restaurant restaurant) {
		return service.createRestaurant(restaurant);
	}

	/**
	 * Endpoint to add a rating to an existing restaurant
	 * 
	 * @param id
	 * @param meal
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@PostMapping("/restaurants/{id}/meal")
	public Restaurant addRestaurantMeal(@PathVariable Long id, @RequestBody Meal meal)
			throws ResourceNotFoundException {
		return service.addRestaurantMeal(id, meal);
	}

}

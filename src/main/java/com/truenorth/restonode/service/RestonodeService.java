package com.truenorth.restonode.service;

import java.util.List;
import java.util.Optional;

import com.truenorth.restonode.dto.CustomerNotificationMessage;
import com.truenorth.restonode.exception.ResourceNotFoundException;
import com.truenorth.restonode.exception.UnfinishedTransactionException;
import com.truenorth.restonode.model.DeliveryOrder;
import com.truenorth.restonode.model.Meal;
import com.truenorth.restonode.model.Rating;
import com.truenorth.restonode.model.Restaurant;

public interface RestonodeService {

	/**
	 * Adds an integer rating to an existing restaurant
	 * 
	 * @param restaurantId
	 * @param rating
	 * @return exception if no restaurant was found with parameter id
	 */
	Restaurant addRestaurantRating(Long restaurantId, Rating rating) throws ResourceNotFoundException;

	/**
	 * Returns restaurants with the given integer rating and if the parameter is not
	 * present it just returns all the restaurants
	 * 
	 * @param rating
	 * @return
	 */
	List<Restaurant> getRestaurants(Optional<Integer> rating);

	/**
	 * Calculates the ETA for the order between the restaurant and the customer's
	 * location, then creates a new DeliveryOrder and attempts to send notification
	 * and order messages to rabbitmq. If this queue is unreachable (e.g. rabbitmq
	 * server's down) this method will throw an exception as the operation is "all
	 * or nothing"-like, i.e. a transaction
	 * 
	 * @param order containing a list of Meal IDs, all belonging to the same
	 *              Restaurant
	 * @return a confirmation message including the ETA of the delivery from the
	 *         restaurant to the customer's address expressed in seconds and "human
	 *         readable" units, and the total amount of the order
	 * @throws UnfinishedTransactionException if it fails to calculate the ETA or to
	 *                                        send RabbitMQ messages
	 */
	CustomerNotificationMessage createDeliveryOrder(DeliveryOrder order) throws UnfinishedTransactionException;

	/**
	 * Returns meals with the given input IDs
	 * 
	 * @param ids a list of meal IDs
	 * @return
	 */
	List<Meal> findMealsInIdList(List<Long> ids);

	/**
	 * Returns all orders
	 * 
	 * @return
	 */
	List<DeliveryOrder> getOrders();

	/**
	 * @param restaurantId
	 * @return an instance of restaurant with the input id
	 * @throws ResourceNotFoundException if there's no such restaurant with the input id
	 */
	Restaurant getRestaurantById(Long id) throws ResourceNotFoundException;

	/**
	 * Creates a restaurant
	 * @param restaurant
	 * @return the restaurant with its new id
	 */
	Restaurant createRestaurant(Restaurant restaurant);

	/**
	 * Adds a Meal to an existing restaurant
	 * 
	 * @param id
	 * @param meal
	 * @return
	 * @throws ResourceNotFoundException if no restaurant was found with input id
	 */
	Restaurant addRestaurantMeal(Long id, Meal meal) throws ResourceNotFoundException;

}
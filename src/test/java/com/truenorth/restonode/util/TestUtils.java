package com.truenorth.restonode.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import com.google.maps.model.LatLng;
import com.truenorth.restonode.model.DeliveryOrder;
import com.truenorth.restonode.model.Meal;
import com.truenorth.restonode.model.Rating;
import com.truenorth.restonode.model.Restaurant;

public class TestUtils {

	public static final String MOCK_ETA = "30 minutes";

	public static DeliveryOrder getMockOrder() {

		Restaurant restaurant = getMockRestaurant();
		Meal noodles = new Meal(restaurant, "Noodles", BigDecimal.valueOf(200));
		Meal cheeseburger = new Meal(restaurant, "Cheeseburger", BigDecimal.valueOf(100));

		return new DeliveryOrder(Arrays.asList(noodles, cheeseburger), "Fake St. 123",
				new LatLng(-34.598284, -58.4175797));
	}

	public static DeliveryOrder getMockOrderWithId() {
		final DeliveryOrder order = getMockOrder();
		order.setId(1L);
		order.setTimestamp(LocalDateTime.now());

		return order;
	}

	public static Restaurant getMockRestaurant() {
		Restaurant restaurant = new Restaurant("Spiagge Di Napoli", new LatLng(-34.6206867, -58.4155187),
				"info@spiagge.com.ar");
		restaurant.setId(1L);

		return restaurant;
	}

	public static Restaurant getMockRestaurantWithRating() {
		Restaurant restaurant = getMockRestaurant();
		restaurant.addRating(new Rating(1));

		return restaurant;
	}

	public static String getMockInvalidRatingAsJson() {
		return "{ \"rating\": -1 }";
	}

}

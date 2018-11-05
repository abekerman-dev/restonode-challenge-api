package com.truenorth.restonode.util;

import java.math.BigDecimal;
import java.util.Arrays;

import com.google.maps.model.Duration;
import com.google.maps.model.LatLng;
import com.truenorth.restonode.model.DeliveryOrder;
import com.truenorth.restonode.model.Meal;
import com.truenorth.restonode.model.Restaurant;

public class TestUtils {

	// FIXME whole method
//	public static DeliveryOrder createOrder() {
//		final DeliveryOrder order = new DeliveryOrder();
//
//		Restaurant restaurant = createRestaurant();
//		order.setRestaurant(restaurant);
//		order.setAddress("Fake St. 123");
//		order.setDestination(new LatLng(-34.598284, -58.4175797));
//
//		Meal noodles = new Meal("Noodles", restaurant);
//		Meal cheeseburger = new Meal("Cheeseburger", restaurant);
//		order.setMeals(Arrays.asList(noodles, cheeseburger));
//
//		order.setTotalAmount(BigDecimal.valueOf(123.45));
//
//		return order;
//	}

	public static Restaurant createRestaurant() {
		Restaurant restaurant = new Restaurant("Spiagge Di Napoli", new LatLng(-34.6206867, -58.4155187), "abekerman@gmail.com");
		restaurant.setId(1L);
		
		return restaurant;
	}

	public static Duration createDuration() {
		final Duration duration = new Duration();
		duration.inSeconds = 1800;
		duration.humanReadable = "30 minutes";

		return duration;
	}

}

package com.truenorth.restonode.util;

import java.math.BigDecimal;
import java.util.Arrays;

import com.google.maps.model.Duration;
import com.google.maps.model.LatLng;
import com.truenorth.restonode.model.DeliveryOrder;
import com.truenorth.restonode.model.Meal;

public class TestUtils {

	public static DeliveryOrder createOrder() {
		final DeliveryOrder order = new DeliveryOrder();
		order.setAddress("Fake St. 123");
		order.setDestination(new LatLng(-34.598284, -58.4175797));

		Meal noodles = new Meal("Noodles", 1);
		Meal cheeseburger = new Meal("Cheeseburger", 2);
		order.setMeals(Arrays.asList(noodles, cheeseburger));

		order.setTotalAmount(BigDecimal.valueOf(123.45));

		return order;
	}
	
	public static Duration createDuration() {
		final Duration duration = new Duration();
		duration.inSeconds = 1800;
		duration.humanReadable = "30 minutes";
		
		return duration;
	}
	
}

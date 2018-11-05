package com.truenorth.restonode.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.truenorth.restonode.messaging.RabbitMQMessage;
import com.truenorth.restonode.model.DeliveryOrder;
import com.truenorth.restonode.model.Meal;

import lombok.Data;
import util.TimestampFormatter;

@Data
public class RestaurantOrderMessage implements RabbitMQMessage {

	// For restaurant reference, to be able to track the order internally
	private Long id;

	private String timestamp;

	private List<String> meals;

	private String address;

	private String toEmail;

	public RestaurantOrderMessage(Long id, String timestamp, List<String> meals, String address,
			String toEmail) {
		super();
		this.id = id;
		this.timestamp = timestamp;
		this.meals = meals;
		this.address = address;
		this.toEmail = toEmail;
	}

	public static RabbitMQMessage fromOrder(DeliveryOrder order) {
		final List<String> meals = order.getMeals().stream().map(Meal::getName).collect(Collectors.toList());

		return new RestaurantOrderMessage(order.getId(), TimestampFormatter.prettyPrint(order.getTimestamp()), meals, order.getAddress(),
				order.getRestaurant().getEmail());
	}

}

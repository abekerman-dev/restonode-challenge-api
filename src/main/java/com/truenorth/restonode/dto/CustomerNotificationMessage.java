package com.truenorth.restonode.dto;

import java.math.BigDecimal;

import com.truenorth.restonode.messaging.RabbitMQMessage;
import com.truenorth.restonode.model.DeliveryOrder;

import lombok.Data;
import util.TimestampFormatter;

@Data
public class CustomerNotificationMessage implements RabbitMQMessage {

	// For customer reference, in case the order gets delayed or lost
	private Long id;

	private String timestamp;

	private String eta;

	private BigDecimal totalAmount;

	public CustomerNotificationMessage(Long id, String timestamp, String eta, BigDecimal totalAmount) {
		super();
		this.id = id;
		this.timestamp = timestamp;
		this.eta = eta;
		this.totalAmount = totalAmount;
	}

	public static CustomerNotificationMessage fromOrder(DeliveryOrder order) {
		return new CustomerNotificationMessage(order.getId(), TimestampFormatter.prettyPrint(order.getTimestamp()),
				order.getEta(), order.getTotalAmount());
	}

}

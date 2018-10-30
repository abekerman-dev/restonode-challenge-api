package com.truenorth.restonode.model;

import lombok.Data;

@Data
public class OrderMessage {
	
	private String toEmail;
	private DeliveryOrder order;

	public OrderMessage(String toEmail, DeliveryOrder order) {
		this.toEmail = toEmail;
		this.order = order;
	}

}

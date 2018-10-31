package com.truenorth.restonode.messaging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OrderSender extends AbstractRabbitMQSender {

	@Value("${spring.rabbitmq.routingKey.order}")
	private String routingKey;
	
	@Override
	protected String getRoutingKey() {
		return routingKey;
	}

}

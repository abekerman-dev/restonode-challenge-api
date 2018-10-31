package com.truenorth.restonode.messaging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NotificationSender extends AbstractRabbitMQSender {

	@Value("${spring.rabbitmq.routingKey.notification}")
	private String routingKey;
	
	@Override
	protected String getRoutingKey() {
		return routingKey;
	}

}

package com.truenorth.restonode.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.truenorth.restonode.model.RestonodeMessageType;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RabbitMQSender {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Value("${rabbitmq.exchange.name}")
	private String exchangeName;

	@Value("${rabbitmq.routingKey}")
	private String routingKey;

	public void send(RestonodeMessageType messageType) {
		log.info("Sending " + messageType.toString());
		rabbitTemplate.convertAndSend(exchangeName, routingKey, messageType.toString());
		log.info("message sent!");
	}

}

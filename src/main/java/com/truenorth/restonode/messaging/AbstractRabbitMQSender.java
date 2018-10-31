package com.truenorth.restonode.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractRabbitMQSender implements RabbitMQSender {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Value("${spring.rabbitmq.exchange.name}")
	protected String exchangeName;

	public void send(Object message) throws Exception {
		String toJson = null;
		toJson = new ObjectMapper().writeValueAsString(message);
		log.debug(this.getClass().getSimpleName() + " sending:\n\t " + toJson + "\nwith exchangeName=" + exchangeName + ", routingKey=" + getRoutingKey());
		rabbitTemplate.convertAndSend(exchangeName, getRoutingKey(), toJson);
		log.debug("Message sent!");
	}

	protected abstract String getRoutingKey();

}

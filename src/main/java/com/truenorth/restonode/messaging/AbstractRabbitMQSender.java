package com.truenorth.restonode.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractRabbitMQSender implements RabbitMQSender {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Value("${rabbitmq.exchange.name}")
	protected String exchangeName;

	public void send(Object message) {
		String toJson = null;
		try {
			toJson = new ObjectMapper().writeValueAsString(message);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("\n\n\n" + this.getClass().getSimpleName() + " sending:\n\t " + toJson + "\nwith exchangeName=" + exchangeName + ", routingKey=" + getRoutingKey());
		rabbitTemplate.convertAndSend(exchangeName, getRoutingKey(), toJson);
		log.debug("\n\n\nMessage sent!");
	}

	protected abstract String getRoutingKey();

}

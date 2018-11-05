package com.truenorth.restonode.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * Sends a RabbitMQ message to a given exchangeName and delegates the definition
 * of which routingKey to use to its subclasses
 * 
 * @author andres
 *
 */
@Slf4j
public abstract class AbstractRabbitMQSender implements RabbitMQSender {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Value("${spring.rabbitmq.exchange.name}")
	protected String exchangeName;

	/**
	 * Converts the input message object into json so the receiving end can parse
	 * the incoming message without needing to count on the actual sent object class
	 * definition
	 */
	public void send(RabbitMQMessage message) throws Exception {
		String stringifiedJson = new ObjectMapper().writeValueAsString(message);
		log.debug(this.getClass().getSimpleName() + " trying to send message with exchangeName=" + exchangeName
				+ ", routingKey=" + getRoutingKey());
		rabbitTemplate.convertAndSend(exchangeName, getRoutingKey(), stringifiedJson);
		log.debug("Sent message follows:");
		log.debug(stringifiedJson);
	}

	protected abstract String getRoutingKey();

}

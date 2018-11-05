package com.truenorth.restonode.messaging;

/**
 * Interface through which RabbitMQ messages are sent
 * @author andres
 *
 */
public interface RabbitMQSender {

	/**
	 * Attempts to send a message to a given RabbitMQ queue
	 * 
	 * @param message
	 * @throws Exception if it fails to send the message
	 */
	public void send(RabbitMQMessage message) throws Exception;
	
}

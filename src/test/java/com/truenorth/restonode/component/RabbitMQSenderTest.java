/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.truenorth.restonode.component;

import static com.truenorth.restonode.util.TestUtils.createDuration;
import static com.truenorth.restonode.util.TestUtils.createOrder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.truenorth.restonode.messaging.RabbitMQSender;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMQSenderTest {

	@Autowired
	@Qualifier("notificationSender")
	private RabbitMQSender notificationSender;

	@Autowired
	@Qualifier("orderSender")
	private RabbitMQSender orderSender;

	@Test
	public void testNotificationSender() throws Exception {
		notificationSender.send(createDuration());
	}

	@Test
	public void testOrderSender() throws Exception {
		orderSender.send(createOrder());
	}

}

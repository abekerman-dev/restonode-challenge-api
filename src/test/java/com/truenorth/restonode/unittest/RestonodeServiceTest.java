package com.truenorth.restonode.unittest;

import static com.truenorth.restonode.util.TestUtils.MOCK_ETA;
import static com.truenorth.restonode.util.TestUtils.getMockOrder;
import static com.truenorth.restonode.util.TestUtils.getMockOrderWithId;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.truenorth.restonode.distanceclient.DistanceMatrixClient;
import com.truenorth.restonode.dto.CustomerNotificationMessage;
import com.truenorth.restonode.dto.RestaurantOrderMessage;
import com.truenorth.restonode.messaging.NotificationSender;
import com.truenorth.restonode.messaging.OrderSender;
import com.truenorth.restonode.model.DeliveryOrder;
import com.truenorth.restonode.repository.DeliveryOrderRepository;
import com.truenorth.restonode.repository.RestaurantRepository;
import com.truenorth.restonode.service.RestonodeServiceImpl;

/**
 * Tests app's service class
 * 
 * @author andres
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class RestonodeServiceTest {

	@Mock
	private RestaurantRepository restaurantRepo;

	@Mock
	private DeliveryOrderRepository orderRepo;

	@Mock
	private NotificationSender notificationSender;

	@Mock
	private OrderSender orderSender;

	@Mock
	private DistanceMatrixClient distanceMatrixClient;

	@InjectMocks
	private RestonodeServiceImpl service;

	@Before
	public void setup() {
		initMocks(this);
	}

	@Test
	public void testCreateDeliveryOrderWithValidOrderCreatesOrder() throws Exception {
		DeliveryOrder order = getMockOrder();
		when(distanceMatrixClient.calculateETA(order.getRestaurant().getLocation(), order.getDestination())).thenReturn(MOCK_ETA);
		DeliveryOrder orderWithId = getMockOrderWithId();
		when(orderRepo.save(order)).thenReturn(orderWithId);
		CustomerNotificationMessage customerNotificationMessage = service.createDeliveryOrder(order);
		assertEquals(Long.valueOf(1), customerNotificationMessage.getId());
		verify(notificationSender).send(CustomerNotificationMessage.fromOrder(orderWithId));
		verify(orderSender).send(RestaurantOrderMessage.fromOrder(orderWithId));
	}

}

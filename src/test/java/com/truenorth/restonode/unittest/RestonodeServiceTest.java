package com.truenorth.restonode.unittest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.truenorth.restonode.distanceclient.DistanceMatrixClient;
import com.truenorth.restonode.messaging.NotificationSender;
import com.truenorth.restonode.messaging.OrderSender;
import com.truenorth.restonode.repository.DeliveryOrderRepository;
import com.truenorth.restonode.repository.RestaurantRepository;
import com.truenorth.restonode.service.RestonodeServiceImpl;

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

	private RestonodeServiceImpl service;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		service = new RestonodeServiceImpl();
		service.setRestaurantRepo(restaurantRepo);
		service.setOrderRepo(orderRepo);
		service.setDistanceMatrixClient(distanceMatrixClient);
		service.setNotificationSender(notificationSender);
		service.setOrderSender(orderSender);
	}

	// TODO improve method name ( when - given - should or similar)
	// FIXME whole method
//	@Test
//	public void testCreateDeliveryOrder() throws Exception {
//		DeliveryOrder newOrder = createOrder();
//		Restaurant restaurant = newOrder.getRestaurant();
//		Duration mockDuration = TestUtils.createDuration();
//
//		when(restaurantRepo.findById(1L)).thenReturn(Optional.of(restaurant));
//
//		when(distanceMatrixClient.calculateDuration(restaurant.getLocation(), newOrder.getDestination()))
//				.thenReturn(mockDuration);
//
//		Duration realDuration = service.createDeliveryOrder(newOrder);
//		Mockito.verify(orderRepo).save(newOrder);
//		assertEquals(mockDuration, realDuration);
//	}

	@Test
	public void testCreateDeliveryOrderRestaurantNotFound() {
		// TODO improve method name ( when - given - should or similar)
		// TODO mock an Optional empty restaurant as return of findById (emulate
		// non-existing id)
		// TODO force EXPECT Exception
	}

	@Test
	public void testFindRestaurantsByRating() {

	}

	@Test
	public void testFindRestaurantsNoRating() {

	}

	@Test
	public void testAddRestaurantRating() {

	}

	@Test
	public void testAddRestaurantNotFoundRating() {
		// TODO force EXPECT Exception
	}

}

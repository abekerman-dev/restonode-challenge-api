package com.truenorth.restonode;

import static com.truenorth.restonode.util.TestUtils.createOrder;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.maps.model.Duration;
import com.google.maps.model.LatLng;
import com.truenorth.restonode.distanceclient.RealDistanceMatrixClient;
import com.truenorth.restonode.messaging.NotificationSender;
import com.truenorth.restonode.messaging.OrderSender;
import com.truenorth.restonode.model.DeliveryOrder;
import com.truenorth.restonode.model.Restaurant;
import com.truenorth.restonode.repository.DeliveryOrderRepository;
import com.truenorth.restonode.repository.RestaurantRepository;
import com.truenorth.restonode.service.RestonodeService;
import com.truenorth.restonode.util.TestUtils;

public class RestonodeServiceTest {

	private RestonodeService service;
	private RestaurantRepository restaurantRepo;
	private DeliveryOrderRepository orderRepo;
	private RealDistanceMatrixClient distanceMatrixClient;
	private NotificationSender notificationSender;
	private OrderSender orderSender;
	
	@Before
	public void setup() {
		restaurantRepo = mock(RestaurantRepository.class);
		orderRepo = mock(DeliveryOrderRepository.class);
		distanceMatrixClient = mock(RealDistanceMatrixClient.class);
		notificationSender = mock(NotificationSender.class);
		orderSender = mock(OrderSender.class);
		
		service = new RestonodeService();
		service.setRestaurantRepo(restaurantRepo);
		service.setOrderRepo(orderRepo);
		service.setDistanceMatrixClient(distanceMatrixClient);
		service.setNotificationSender(notificationSender);
		service.setOrderSender(orderSender);
	}
	
	@Test
	public void testCreateDeliveryOrder() throws Exception {
		// TODO improve method name ( when - given - should or similar)
		DeliveryOrder order = createOrder();
		Restaurant restaurant = new Restaurant("Spiagge Di Napoli", new LatLng(-34.6206867, -58.4155187), "abekerman@gmail.com");
		Duration duration = TestUtils.createDuration();

		when(restaurantRepo
			.findById(123L))
			.thenReturn(Optional.of(restaurant));
		
		when(distanceMatrixClient
			.calculateDuration(restaurant.getLocation(), order.getDestination()))
			.thenReturn(duration);
		Duration expectedDuration = service.createDeliveryOrder(123L, order);
	
		Mockito.verify(orderRepo).save(order);
		
		assertEquals(expectedDuration, duration);
	}
	
	@Test
	public void testCreateDeliveryOrderRestaurantNotFound() {
		// TODO improve method name ( when - given - should or similar)
		// TODO mock an Optional empty restaurant as return of findById (emulate non-existing id)
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

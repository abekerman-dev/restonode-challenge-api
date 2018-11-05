package com.truenorth.restonode.unittest;

import static com.truenorth.restonode.util.TestUtils.createRestaurant;
import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import com.truenorth.restonode.model.Restaurant;
import com.truenorth.restonode.util.TestUtils;

public class RestaurantTest {

	private static final Restaurant aRestaurant = createRestaurant();
	
	@Test
	public void testEquals() {
		assertEquals(aRestaurant, TestUtils.createRestaurant());
	}
	
	@Test 
	public void testNotEquals() {
		Restaurant anotherRestaurant = createRestaurant();
		anotherRestaurant.setId(2L);
		Assert.assertNotEquals(aRestaurant, anotherRestaurant);
	}

}

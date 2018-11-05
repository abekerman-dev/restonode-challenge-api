package com.truenorth.restonode.unittest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RestonodeControllerTest {
	
	@Before
	public void setup() {
		
	}

	@Test
	public void testAddRestaurantValidRating( ) {
		// TODO mock code so that rating gets added to restaurant correctly
	}
	
	@Test
	public void testAddRestaurantInvalidRating( ) {
		// TODO mock code so that rating does not get added to restaurant correctly
	}

	@Test
	public void testCreateValidOrder( ) {
		// TODO mock code so that controller creates order
	}

	@Test
	public void testCreateInvalidOrder( ) {
		// TODO mock code so that controller throws InvalidOrderException
	}
	

}

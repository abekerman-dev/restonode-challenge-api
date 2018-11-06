package com.truenorth.restonode.unittest;

import static com.truenorth.restonode.util.TestUtils.getMockInvalidRatingAsJson;
import static com.truenorth.restonode.util.TestUtils.getMockRestaurant;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.truenorth.restonode.controller.ExceptionHandlerAdvice;
import com.truenorth.restonode.controller.RestonodeController;
import com.truenorth.restonode.exception.InvalidRatingException;
import com.truenorth.restonode.model.Rating;
import com.truenorth.restonode.service.RestonodeService;

/**
 * Tests app's controller class
 * 
 * @author andres
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RestonodeControllerTest {

	private MockMvc mockMvc;

	@Mock
	private RestonodeService service;

	@InjectMocks
	private RestonodeController controller;

	@Before
	public void init() {
		initMocks(this);
		mockMvc = standaloneSetup(controller).setControllerAdvice(ExceptionHandlerAdvice.class)
				.build();
	}

	@Test
	public void whenGetRestaurantsExpectStatusOkAndId1() throws Exception {

		when(service.getRestaurants(Optional.empty())).thenReturn(Arrays.asList(getMockRestaurant()));

		MvcResult result = mockMvc.perform(get("/restaurants"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andReturn();

		String jsonResponse = result.getResponse()
				.getContentAsString();
		JsonElement jsonTree = new JsonParser().parse(jsonResponse);

		assertEquals(1L, jsonTree.getAsJsonArray()
				.get(0)
				.getAsJsonObject()
				.get("id")
				.getAsLong());
	}

	@Test
	public void givenInvalidRatingWhenPostRestaurantRatingExpectBadRequest() throws Exception {
		when(service.addRestaurantRating(1L, new Rating(-1))).thenThrow(InvalidRatingException.class);
		String requestJson = getMockInvalidRatingAsJson();
		mockMvc.perform(post("/restaurants/1/rating").contentType(APPLICATION_JSON_UTF8)
				.content(requestJson))
				.andExpect(status().isBadRequest());
	}

}

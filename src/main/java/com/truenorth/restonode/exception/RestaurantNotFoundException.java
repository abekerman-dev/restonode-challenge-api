package com.truenorth.restonode.exception;

@SuppressWarnings("serial")
public class RestaurantNotFoundException extends RuntimeException {

	public RestaurantNotFoundException(Long id) {
		super("Could not find restaurant " + id);
	}
}

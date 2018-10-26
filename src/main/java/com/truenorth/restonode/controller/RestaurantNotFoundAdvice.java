package com.truenorth.restonode.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.truenorth.restonode.exception.RestaurantNotFoundException;

@ControllerAdvice
public class RestaurantNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(RestaurantNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String restaurantNotFoundHandler(RestaurantNotFoundException ex) {
		return ex.getMessage();
	}
}

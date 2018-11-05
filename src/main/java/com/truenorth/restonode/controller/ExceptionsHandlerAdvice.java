package com.truenorth.restonode.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.truenorth.restonode.exception.InvalidOrderException;
import com.truenorth.restonode.exception.InvalidRatingException;
import com.truenorth.restonode.exception.ResourceNotFoundException;
import com.truenorth.restonode.exception.UnfinishedTransactionException;

/**
 * Class featuring exception handlers for client-side (4xx) and server-side
 * (5xx) errors
 * 
 * @author andres
 *
 */
@ControllerAdvice
public class ExceptionsHandlerAdvice {

	@ResponseBody
	@ExceptionHandler(InvalidRatingException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String invalidRatingExceptionExceptionHandler(final InvalidRatingException ex) {
		return ex.getMessage();
	}

	@ResponseBody
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String resourcetNotFoundExceptionHandler(final ResourceNotFoundException ex) {
		return ex.getMessage();
	}

	@ResponseBody
	@ExceptionHandler(InvalidOrderException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String invalidOrderExceptionHandler(final InvalidOrderException ex) {
		return ex.getMessage();
	}

	@ResponseBody
	@ExceptionHandler(UnfinishedTransactionException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	String unfinishedTransactionExceptionHandler(final UnfinishedTransactionException ex) {
		StringBuilder errorDetail = new StringBuilder();
		errorDetail.append("INTERNAL SERVER ERROR:");
		errorDetail.append("\n\t" + ex.getMessage());
		errorDetail.append("\n\t\t" + ex.getCause().getMessage());

		return errorDetail.toString();
	}

}

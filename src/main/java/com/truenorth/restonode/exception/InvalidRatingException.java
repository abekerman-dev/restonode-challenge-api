package com.truenorth.restonode.exception;

@SuppressWarnings("serial")
public class InvalidRatingException extends Exception {

	public InvalidRatingException() {
		super("Rating must be between 1 and 5");
	}
	
}

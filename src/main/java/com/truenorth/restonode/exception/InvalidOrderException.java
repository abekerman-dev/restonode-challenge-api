package com.truenorth.restonode.exception;

@SuppressWarnings("serial")
public class InvalidOrderException extends Exception {

	public InvalidOrderException() {
		super("The attempted POSTed order includes meals from different restaurants. Meals from a single restaurant allowed only");
	}

}

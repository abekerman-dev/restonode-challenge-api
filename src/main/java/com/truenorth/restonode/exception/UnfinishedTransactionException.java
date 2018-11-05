package com.truenorth.restonode.exception;

/**
 * Exception representing an unfinished transaction (i.e. one that failed
 * halfway through)
 * 
 * @author andres
 *
 */
@SuppressWarnings("serial")
public class UnfinishedTransactionException extends Exception {

	public UnfinishedTransactionException(Throwable cause) {
		super("Failed to complete transaction", cause);
	}

}

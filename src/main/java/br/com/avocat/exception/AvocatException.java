package br.com.avocat.exception;

import java.util.UUID;

/**
 * @author Michael
 * 
 * Exception customizada.
 */
public class AvocatException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AvocatException(String msg) {
		super("UUID AvocatException: " + UUID.randomUUID() + msg);
	}	
}

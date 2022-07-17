package br.com.avocat.exception;

import java.io.Serial;
import java.util.UUID;

/**
 * @author Michael
 * 
 * Exception customizada.
 */
public class AvocatException extends RuntimeException {
	@Serial
	private static final long serialVersionUID = 1L;

	public AvocatException(String msg) {
		super(msg);
	}

	public AvocatException(Exception e) {
		super(e);
	}
}

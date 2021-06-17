package net.apimessages.pd2.exceptions;

public class BadRequest extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public BadRequest(String exception) {
		super(exception);
	}
}
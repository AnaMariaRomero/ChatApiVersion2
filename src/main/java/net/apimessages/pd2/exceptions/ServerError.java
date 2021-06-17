package net.apimessages.pd2.exceptions;

public class ServerError extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ServerError(String exception) {
		super(exception);
	}
}

package com.webcheckers.rest.exceptions;

public class CantCreateGameException extends RuntimeException{

	private static final long serialVersionUID = -5619533540026861601L;
	
	public CantCreateGameException(int id) {
		super("Invalid ID or game already created. ID: " + id);
	}
}

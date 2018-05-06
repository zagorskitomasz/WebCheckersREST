package com.webcheckers.rest.exceptions;

public class CantSaveGameException extends RuntimeException{

	private static final long serialVersionUID = -5619533540026861601L;
	
	public CantSaveGameException(int id) {
		super("Invalid ID or game already archivised. ID: " + id);
	}
}

package com.webcheckers.rest.exceptions;

public class GameNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 7495948282747820486L;
	
	public GameNotFoundException(int id) {
		super("Game not found! Invalid ID: " + id);
	}
}

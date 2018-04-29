package com.webcheckers.rest.service;

import com.webcheckers.rest.domain.Game;

public interface GameService {

	public boolean saveGame(Game game);
	public Game loadGame(String name, String password);
}

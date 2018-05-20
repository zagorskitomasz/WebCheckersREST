package com.webcheckers.rest.service;

import com.webcheckers.rest.domain.GameStarter;
import com.webcheckers.rest.domain.LightGame;

public interface LightGameService {

	public boolean saveGame(LightGame game);
	public LightGame loadGame(int id);
	public boolean createGame(GameStarter game);
}

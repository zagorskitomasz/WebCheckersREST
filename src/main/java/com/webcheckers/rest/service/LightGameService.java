package com.webcheckers.rest.service;

import com.webcheckers.rest.domain.LightGame;

public interface LightGameService {

	public void saveGame(LightGame game);
	public LightGame loadGame(int id);
}

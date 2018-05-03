package com.webcheckers.rest.dao;

import com.webcheckers.rest.domain.LightGame;

public interface LightGameRepository {
	
	public boolean insert(LightGame game);
	public boolean updateState(LightGame game);
	public boolean delete(int id);
	public LightGame selectOne(int id);
	public boolean executeUpdate(String query);
}

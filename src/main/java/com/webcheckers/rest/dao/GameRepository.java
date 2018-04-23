package com.webcheckers.rest.dao;

import java.util.List;

import com.webcheckers.rest.domain.Game;

public interface GameRepository {
	
	public boolean insert(Game game);
	
	public boolean updateStateByName(Game game);
	public boolean updateStateById(Game game);
	
	public boolean delete(long id);
	public boolean delete(String name);
	
	public Game selectOne(long id);
	public Game selectOne(String name);
	
	public List<Game> selectAll();
	public List<Game> selectAll(String filter);
}

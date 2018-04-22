package com.webcheckers.rest.dao;

import java.util.List;

import com.webcheckers.rest.domain.Game;

public interface GameRepository {
	
	public int insert(Game game);
	public void updateById(Game game);
	public void updateByName(Game game);
	public void deleteById(Game game);
	public void deleteByName(Game game);
	public Game selectOneById(int id);
	public Game selectOneByName(String name);
	public List<Game> selectWhere(String clause);
}

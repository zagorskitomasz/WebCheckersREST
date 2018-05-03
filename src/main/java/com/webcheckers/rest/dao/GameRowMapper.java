package com.webcheckers.rest.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.webcheckers.rest.domain.LightGame;

@Component
public class GameRowMapper implements RowMapper<LightGame>{

	@Override
	public LightGame mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
		
		LightGame game = mapGame(resultSet);
		
		return game;
	}

	private LightGame mapGame(ResultSet resultSet) throws SQLException {
		
		LightGame game = new LightGame();
		game.setId(resultSet.getInt("ID"));
		game.setState(resultSet.getString("STATE"));
		
		return game;
	}
}

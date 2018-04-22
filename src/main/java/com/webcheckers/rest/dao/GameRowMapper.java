package com.webcheckers.rest.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.webcheckers.rest.domain.Game;
import com.webcheckers.rest.domain.Player;

@Component
public class GameRowMapper implements RowMapper<Game>{

	@Override
	public Game mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
		
		Player playerBlack = mapPlayer(resultSet, "BLACK");
		Player playerWhite = mapPlayer(resultSet, "WHITE");
		
		Game game = mapGame(resultSet);
		
		game.setPlayerBlack(playerBlack);
		game.setPlayerWhite(playerWhite);
		
		return game;
	}

	private Game mapGame(ResultSet resultSet) throws SQLException {
		
		Game game = new Game();
		game.setId(resultSet.getInt("ID"));
		game.setName(resultSet.getString("NAME"));
		game.setPassword(resultSet.getString("PASSWORD"));
		game.setState(resultSet.getString("STATE"));
		
		return game;
	}

	private Player mapPlayer(ResultSet resultSet, String color) throws SQLException {
		
		Player player = new Player();
		
		player.setId(resultSet.getInt("PLAYER_" + color));
		player.setLogin(resultSet.getString("PL_" + color + "_LOGIN"));
		player.setPassword(resultSet.getString("PL_" + color + "_PASSWORD"));
		player.setEmail(resultSet.getString("PL_" + color + "_EMAIL"));
		
		return player;
	}
}

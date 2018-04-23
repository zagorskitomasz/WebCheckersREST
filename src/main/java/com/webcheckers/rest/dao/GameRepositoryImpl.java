package com.webcheckers.rest.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import com.webcheckers.rest.domain.Game;

@Repository
public class GameRepositoryImpl implements GameRepository {

	private static final String PM_ID = "ID";
	private static final String PM_NAME = "NAME";
	private static final String PM_PASSWORD = "PASSWORD";
	private static final String PM_STATE = "STATE";
	private static final String PM_PLAYER_BLACK = "PLAYER_BLACK";
	private static final String PM_PLAYER_WHITE = "PLAYER_WHITE";
	
	private static final String INSERT_QUERY = " INSERT INTO GAMES (NAME, PASSWORD, STATE, PLAYER_BLACK, PLAYER_WHITE) VALUES (?, ?, ?, ?, ?) ";
	private static final String INSERT_DEF_PLAYERS_QUERY = " INSERT INTO GAMES (NAME, PASSWORD, STATE) VALUES (?, ?, ?) ";
	private static final String UPDATE_BY_ID_QUERY = " UPDATE GAMES SET STATE = ? WHERE ID = ? ";
	private static final String UPDATE_BY_NAME_QUERY = " UPDATE GAMES SET STATE = ? WHERE NAME = ? ";
	private static final String DELETE_BY_ID_QUERY = " DELETE FROM GAMES WHERE ID = ? ";
	private static final String DELETE_BY_NAME_QUERY = " DELETE FROM GAMES WHERE NAME = ? ";
	private static final String SELECT_BY_NAME_QUERY = " SELECT G.ID AS ID, G.NAME AS NAME, G.PASSWORD AS PASSWORD, G.STATE AS STATE, PB.ID AS PL_BLACK_ID, PB.LOGIN AS PL_BLACK_LOGIN, PB.PASSWORD AS PL_BLACK_PASSWORD, PB.EMAIL AS PL_BLACK_EMAIL, PW.ID AS PL_WHITE_ID, PW.LOGIN AS PL_WHITE_LOGIN, PW.PASSWORD AS PL_WHITE_PASSWORD, PW.EMAIL AS PL_WHITE_EMAIL FROM GAMES G JOIN PLAYERS PB ON G.PLAYER_BLACK = PB.ID JOIN PLAYERS PW ON G.PLAYER_WHITE = PW.ID WHERE G.NAME = ? ";
	private static final String SELECT_BY_ID_QUERY = " SELECT G.ID AS ID, G.NAME AS NAME, G.PASSWORD AS PASSWORD, G.STATE AS STATE, PB.ID AS PL_BLACK_ID, PB.LOGIN AS PL_BLACK_LOGIN, PB.PASSWORD AS PL_BLACK_PASSWORD, PB.EMAIL AS PL_BLACK_EMAIL, PW.ID AS PL_WHITE_ID, PW.LOGIN AS PL_WHITE_LOGIN, PW.PASSWORD AS PL_WHITE_PASSWORD, PW.EMAIL AS PL_WHITE_EMAIL FROM GAMES G JOIN PLAYERS PB ON G.PLAYER_BLACK = PB.ID JOIN PLAYERS PW ON G.PLAYER_WHITE = PW.ID WHERE G.ID = ? ";
	private static final String SELECT_ALL_QUERY = " SELECT G.ID AS ID, G.NAME AS NAME, G.PASSWORD AS PASSWORD, G.STATE AS STATE, PB.ID AS PL_BLACK_ID, PB.LOGIN AS PL_BLACK_LOGIN, PB.PASSWORD AS PL_BLACK_PASSWORD, PB.EMAIL AS PL_BLACK_EMAIL, PW.ID AS PL_WHITE_ID, PW.LOGIN AS PL_WHITE_LOGIN, PW.PASSWORD AS PL_WHITE_PASSWORD, PW.EMAIL AS PL_WHITE_EMAIL FROM GAMES G JOIN PLAYERS PB ON G.PLAYER_BLACK = PB.ID JOIN PLAYERS PW ON G.PLAYER_WHITE = PW.ID WHERE 1=1 ";
	
	@Autowired 
	private JdbcOperations jdbcOperations;
	
	@Autowired
	private GameRowMapper rowMapper;
	
	@Override
	public boolean insert(Game game) {
		
		try {
			if(game.getPlayerBlack() == null || game.getPlayerWhite() == null)
				jdbcOperations.update(INSERT_DEF_PLAYERS_QUERY, game.getName(), game.getPassword(), game.getState());
			else
				jdbcOperations.update(INSERT_QUERY, game.getName(), game.getPassword(), game.getState(), game.getPlayerBlack().getId(), game.getPlayerWhite().getId());
			return true;
		}
		catch(DuplicateKeyException exception) {
			return false;
		}
	}

	@Override
	public boolean updateStateByName(Game game) {
		
		return jdbcOperations.update(UPDATE_BY_NAME_QUERY, game.getState(), game.getName()) == 1;
	}

	@Override
	public boolean updateStateById(Game game) {
		
		return jdbcOperations.update(UPDATE_BY_ID_QUERY, game.getState(), game.getId()) == 1;
	}

	@Override
	public boolean delete(long id) {
		
		return jdbcOperations.update(DELETE_BY_ID_QUERY, id) == 1;
	}

	@Override
	public boolean delete(String name) {
		
		return jdbcOperations.update(DELETE_BY_NAME_QUERY, name) == 1;
	}

	@Override
	public Game selectOne(long id) {
		
		return jdbcOperations.queryForObject(SELECT_BY_ID_QUERY, rowMapper, id);
	}

	@Override
	public Game selectOne(String name) {
		
		return jdbcOperations.queryForObject(SELECT_BY_NAME_QUERY, rowMapper, name);
	}

	@Override
	public List<Game> selectAll() {
		
		return jdbcOperations.query(SELECT_ALL_QUERY, rowMapper);
	}

	@Override
	public List<Game> selectAll(String filter) {

		return jdbcOperations.query(SELECT_ALL_QUERY + " AND " + filter, rowMapper);
	}
}

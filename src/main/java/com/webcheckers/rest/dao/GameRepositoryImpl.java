package com.webcheckers.rest.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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
	
	private static final String INSERT_QUERY = " INSERT INTO GAMES (NAME, PASSWORD, STATE, PLAYER_BLACK, PLAYER_WHITE) VALUES (:NAME, :PASSWORD, :STATE, :PLAYER_BLACK, :PLAYER_WHITE) ";
	private static final String INSERT_DEF_PLAYERS_QUERY = " INSERT INTO GAMES (NAME, PASSWORD, STATE) VALUES (:NAME, :PASSWORD, :STATE) ";
	private static final String UPDATE_BY_ID_QUERY = " UPDATE GAMES SET STATE = :STATE WHERE ID = :ID ";
	private static final String UPDATE_BY_NAME_QUERY = " UPDATE GAMES SET STATE = :STATE WHERE NAME = :NAME ";
	private static final String DELETE_BY_ID_QUERY = " DELETE FROM GAMES WHERE ID = :ID ";
	private static final String DELETE_BY_NAME_QUERY = " DELETE FROM GAMES WHERE NAME = :NAME ";
	private static final String SELECT_BY_NAME_QUERY = " SELECT G.ID AS ID, G.NAME AS NAME, G.PASSWORD AS PASSWORD, G.STATE AS STATE, PB.ID AS PL_BLACK_ID, PB.LOGIN AS PL_BLACK_LOGIN, PB.PASSWORD AS PL_BLACK_PASSWORD, PB.EMAIL AS PL_BLACK_EMAIL, PW.ID AS PL_WHITE_ID, PW.LOGIN AS PL_WHITE_LOGIN, PW.PASSWORD AS PL_WHITE_PASSWORD, PW.EMAIL AS PL_WHITE_EMAIL FROM GAMES G JOIN PLAYERS PB ON G.PLAYER_BLACK = PB.ID JOIN PLAYERS PW ON G.PLAYER_WHITE = PW.ID WHERE G.NAME = :NAME ";
	private static final String SELECT_BY_ID_QUERY = " SELECT G.ID AS ID, G.NAME AS NAME, G.PASSWORD AS PASSWORD, G.STATE AS STATE, PB.ID AS PL_BLACK_ID, PB.LOGIN AS PL_BLACK_LOGIN, PB.PASSWORD AS PL_BLACK_PASSWORD, PB.EMAIL AS PL_BLACK_EMAIL, PW.ID AS PL_WHITE_ID, PW.LOGIN AS PL_WHITE_LOGIN, PW.PASSWORD AS PL_WHITE_PASSWORD, PW.EMAIL AS PL_WHITE_EMAIL FROM GAMES G JOIN PLAYERS PB ON G.PLAYER_BLACK = PB.ID JOIN PLAYERS PW ON G.PLAYER_WHITE = PW.ID WHERE G.ID = :ID ";
	private static final String SELECT_ALL_QUERY = " SELECT G.ID AS ID, G.NAME AS NAME, G.PASSWORD AS PASSWORD, G.STATE AS STATE, PB.ID AS PL_BLACK_ID, PB.LOGIN AS PL_BLACK_LOGIN, PB.PASSWORD AS PL_BLACK_PASSWORD, PB.EMAIL AS PL_BLACK_EMAIL, PW.ID AS PL_WHITE_ID, PW.LOGIN AS PL_WHITE_LOGIN, PW.PASSWORD AS PL_WHITE_PASSWORD, PW.EMAIL AS PL_WHITE_EMAIL FROM GAMES G JOIN PLAYERS PB ON G.PLAYER_BLACK = PB.ID JOIN PLAYERS PW ON G.PLAYER_WHITE = PW.ID WHERE 1=1 ";
	
	@Autowired 
	private NamedParameterJdbcOperations jdbcOperations;
	
	@Autowired
	private GameRowMapper rowMapper;
	
	@Override
	public boolean insert(Game game) {
		
		SqlParameterSource parameterSource = buildParams(game);
		
		try {
			if(game.getPlayerBlack() == null || game.getPlayerWhite() == null)
				jdbcOperations.update(INSERT_DEF_PLAYERS_QUERY, parameterSource);
			else
				jdbcOperations.update(INSERT_QUERY, parameterSource);
			return true;
		}
		catch(DuplicateKeyException exception) {
			return false;
		}
	}

	@Override
	public boolean updateStateByName(Game game) {
		
		return jdbcOperations.update(UPDATE_BY_NAME_QUERY, buildParams(game)) == 1;
	}

	@Override
	public boolean updateStateById(Game game) {
		
		return jdbcOperations.update(UPDATE_BY_ID_QUERY, buildParams(game)) == 1;
	}

	@Override
	public boolean delete(long id) {

		Game paramsHolder = new Game();
		paramsHolder.setId(id);
		
		return jdbcOperations.update(DELETE_BY_ID_QUERY, buildParams(paramsHolder)) == 1;
	}

	@Override
	public boolean delete(String name) {
		
		Game paramsHolder = new Game();
		paramsHolder.setName(name);
		
		return jdbcOperations.update(DELETE_BY_NAME_QUERY, buildParams(paramsHolder)) == 1;
	}

	@Override
	public Game selectOne(long id) {

		Game paramsHolder = new Game();
		paramsHolder.setId(id);
		
		return jdbcOperations.queryForObject(SELECT_BY_ID_QUERY, buildParams(paramsHolder), rowMapper);
	}

	@Override
	public Game selectOne(String name) {
		
		Game paramsHolder = new Game();
		paramsHolder.setName(name);
		
		return jdbcOperations.queryForObject(SELECT_BY_NAME_QUERY, buildParams(paramsHolder), rowMapper);
	}

	@Override
	public List<Game> selectAll() {
		
		return jdbcOperations.query(SELECT_ALL_QUERY, rowMapper);
	}

	@Override
	public List<Game> selectAll(String filter) {

		return jdbcOperations.query(SELECT_ALL_QUERY + " AND " + filter, rowMapper);
	}
	
	@Override
	public boolean executeUpdate(String query) {
		
		return jdbcOperations.update(query, new MapSqlParameterSource()) > 0;
	}

	private MapSqlParameterSource buildParams(Game game) {
		
		return new MapSqlParameterSource()
				.addValue(PM_ID, game.getId())
				.addValue(PM_NAME, game.getName())
				.addValue(PM_PASSWORD, game.getPassword())
				.addValue(PM_PLAYER_BLACK, game.getPlayerBlack() != null ? game.getPlayerBlack().getId() : null)
				.addValue(PM_PLAYER_WHITE, game.getPlayerWhite() != null ? game.getPlayerWhite().getId() : null)
				.addValue(PM_STATE, game.getState());
	}
}

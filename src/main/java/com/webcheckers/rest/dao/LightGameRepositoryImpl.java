package com.webcheckers.rest.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.webcheckers.rest.domain.GameStarter;
import com.webcheckers.rest.domain.LightGame;

@Repository
public class LightGameRepositoryImpl implements LightGameRepository {

	private static final String PM_ID = "ID";
	private static final String PM_STATE = "STATE";
	
	private static final String INSERT_QUERY = " INSERT INTO GAMES (STATE) VALUES (:STATE) ";
	private static final String INSERT_STARTER_QUERY = " INSERT INTO GAMES (ID) VALUES (:ID) ";
	private static final String UPDATE_QUERY = " UPDATE GAMES SET STATE = :STATE WHERE ID = :ID ";
	private static final String DELETE_QUERY = " DELETE FROM GAMES WHERE ID = :ID ";
	private static final String SELECT_QUERY = " SELECT G.ID AS ID, G.STATE AS STATE FROM GAMES G WHERE G.ID = :ID ";
	
	@Autowired 
	private NamedParameterJdbcOperations jdbcOperations;
	
	@Autowired
	private GameRowMapper rowMapper;
	
	@Override
	public boolean insert(LightGame game) {
		
		SqlParameterSource parameterSource = buildParams(game);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		boolean result = jdbcOperations.update(INSERT_QUERY, parameterSource, keyHolder) == 1;
		game.setId((int)keyHolder.getKeys().get(PM_ID));
		
		return result;
	}
	
	@Override
	public boolean insert(GameStarter game) {
		
		SqlParameterSource parameterSource = buildParams(game);
		
		return jdbcOperations.update(INSERT_STARTER_QUERY, parameterSource) == 1;
	}

	@Override
	public boolean updateState(LightGame game) {
		
		return jdbcOperations.update(UPDATE_QUERY, buildParams(game)) == 1;
	}

	@Override
	public boolean delete(int id) {

		LightGame paramsHolder = new LightGame();
		paramsHolder.setId(id);
		
		return jdbcOperations.update(DELETE_QUERY, buildParams(paramsHolder)) == 1;
	}

	@Override
	public LightGame selectOne(int id) {

		LightGame paramsHolder = new LightGame();
		paramsHolder.setId(id);
		
		return jdbcOperations.queryForObject(SELECT_QUERY, buildParams(paramsHolder), rowMapper);
	}
	
	@Override
	public boolean executeUpdate(String query) {
		
		return jdbcOperations.update(query, new MapSqlParameterSource()) > 0;
	}

	private MapSqlParameterSource buildParams(LightGame game) {
		
		return new MapSqlParameterSource()
				.addValue(PM_ID, game.getId())
				.addValue(PM_STATE, game.getState());
	}

	private MapSqlParameterSource buildParams(GameStarter game) {
		
		return new MapSqlParameterSource()
				.addValue(PM_ID, game.getId());
	}
}

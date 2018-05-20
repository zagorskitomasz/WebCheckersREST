package com.webcheckers.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webcheckers.rest.dao.LightGameRepository;
import com.webcheckers.rest.domain.GameStarter;
import com.webcheckers.rest.domain.LightGame;

@Service
public class LightGameServiceImpl implements LightGameService{
	
	private static final String ARCHIVISE_QUERY = " CALL ARCHIVISE(:ID, :WINNER) ";

	@Autowired
	private LightGameRepository repository;
	
	@Override
	public LightGame loadGame(int id) {
		
		return repository.selectOne(id);
	}
	
	@Override
	public boolean saveGame(LightGame game) {
		
		if(game.getWinner() != null && "WwBb".contains(game.getWinner()))
			return archivise(game);
		else
			return repository.updateState(game);
	}
	
	private boolean archivise(LightGame game) {
		
		return repository.executeUpdate(ARCHIVISE_QUERY
				.replaceAll(":ID", String.valueOf(game.getId()))
				.replaceAll(":WINNER", "'" + game.getWinner() + "'"));
	}

	@Override
	public boolean createGame(GameStarter game) {
		
		return repository.insert(game);
	}
}

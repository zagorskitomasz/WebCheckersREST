package com.webcheckers.rest.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.webcheckers.rest.dao.LightGameRepository;
import com.webcheckers.rest.domain.LightGame;

public class LightGameServiceImpl implements LightGameService{
	
	private static final String ARCHIVISE_QUERY = " CALL ARCHIVISE(:ID, :WINNER) ";

	@Autowired
	private LightGameRepository repository;
	
	@Override
	public LightGame loadGame(int id) {
		
		return repository.selectOne(id);
	}
	
	@Override
	public void saveGame(LightGame game) {
		
		if(game.getWinner() != null && "WwBb".contains(game.getWinner()))
			archivise(game);
		else
			repository.updateState(game);
	}
	
	private void archivise(LightGame game) {
		
		repository.executeUpdate(ARCHIVISE_QUERY
				.replaceAll(":ID", String.valueOf(game.getId()))
				.replaceAll(":WINNER", game.getWinner()));
	}
}
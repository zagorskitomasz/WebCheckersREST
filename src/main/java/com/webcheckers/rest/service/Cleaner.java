package com.webcheckers.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.webcheckers.rest.dao.GameRepository;

@Component
public class Cleaner {

	private static final String CLEAN_QUERY = "DELETE FROM GAMES WHERE GAMES.UPDATE_TIME < DATE_ADD(CURRENT_DATE, INTERVAL -7 DAY)";

	@Autowired
	private GameRepository gameRepository;
	
	@Scheduled(fixedDelay=3600000)
	public void cleanDeadGames() {
		
		gameRepository.executeUpdate(CLEAN_QUERY);
	}
}

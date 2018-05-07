package com.webcheckers.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.webcheckers.rest.domain.LightGame;
import com.webcheckers.rest.exceptions.CantSaveGameException;
import com.webcheckers.rest.exceptions.GameNotFoundException;
import com.webcheckers.rest.service.LightGameService;

@RestController
@RequestMapping("/state")
public class GameStateController {

	@Autowired
	private LightGameService service;
	
	@GetMapping("/load/{id}")
	public @ResponseBody LightGame loadGame(@PathVariable int id) {
			
		try {
			return service.loadGame(id);
		}
		catch(EmptyResultDataAccessException ex) {
			throw new GameNotFoundException(id);
		}
	}
	
	@PostMapping("/save")
	public void saveGame(@RequestBody LightGame game) {
		
		if(!service.saveGame(game))
			throw new CantSaveGameException(game.getId());
	}
	
	@RequestMapping("/wake-up")
	public void wakeUp() {}
	
	@ExceptionHandler(GameNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String gameNotFound(GameNotFoundException ex) {
		
		return ex.getMessage();
	}
	
	@ExceptionHandler(CantSaveGameException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String cantSaveGame(CantSaveGameException ex) {
		
		return ex.getMessage();
	}
}

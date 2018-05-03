package com.webcheckers.rest.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.webcheckers.rest.domain.LightGame;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LightGameRepositoryImplTest {

	@Autowired
	private LightGameRepository gameRepository;

	@Test
	@Transactional
	@Rollback(true)
	public void insertDefSelectOneDelete() {
		
		LightGame game = new LightGame();
		game.setState("asd");
		
		gameRepository.insert(game);
		
		LightGame selected = gameRepository.selectOne(game.getId());
		
		Assert.assertEquals(game.getState(), selected.getState());
		
		gameRepository.delete(selected.getId());
	}
}

package com.webcheckers.rest.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.webcheckers.rest.domain.Game;
import com.webcheckers.rest.domain.Player;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameRepositoryImplTest {

	@Autowired
	private GameRepository gameRepository;
	
	@Test
	public void insertDefSelectOneDelete() {
		
		Game game = new Game();
		game.setName("TEST_N");
		game.setPassword("TEST_P");
		
		gameRepository.insert(game);
		
		Game selected = gameRepository.selectOne(game.getName());
		
		Assert.assertEquals(game.getName(), selected.getName());
		Assert.assertEquals(1, selected.getPlayerBlack().getId());
		
		gameRepository.delete(selected.getId());
	}
	
	@Test
	public void insertUpdateSelectWhereDelete() {
		
		Game game1 = new Game();
		game1.setName("TEST_N1");
		game1.setState("TEST_P1");
		game1.setPlayerBlack(new Player(1));
		game1.setPlayerWhite(new Player(1));
		
		Game game2 = new Game();
		game2.setName("TEST_N2");
		game2.setState("TEST_P2");
		game2.setPlayerBlack(new Player(1));
		game2.setPlayerWhite(new Player(1));
		
		gameRepository.insert(game1);
		gameRepository.insert(game2);
		
		game2.setState("TEST_P1");
		
		gameRepository.updateStateByName(game2);
		
		List<Game> games = gameRepository.selectAll(" G.STATE = 'TEST_P1'");
		
		Assert.assertTrue(2 == games.size());
		
		games.forEach(game -> gameRepository.delete(game.getId()));
	}
}

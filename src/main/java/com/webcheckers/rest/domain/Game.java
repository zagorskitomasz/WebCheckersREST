package com.webcheckers.rest.domain;

public class Game {

	private int id;
	private String name;
	private String password;
	private String state;
	private Player playerBlack;
	private Player playerWhite;

	public Game() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Player getPlayerBlack() {
		return playerBlack;
	}

	public void setPlayerBlack(Player playerBlack) {
		this.playerBlack = playerBlack;
	}

	public Player getPlayerWhite() {
		return playerWhite;
	}

	public void setPlayerWhite(Player playerWhite) {
		this.playerWhite = playerWhite;
	}
}

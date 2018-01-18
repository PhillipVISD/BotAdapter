package Bots;

import Interact.ExplainOnCreate;

import java.util.Random;

public class SoccerBot implements ExplainOnCreate {
	int jerseyNumber;
	public String playerName;

	public SoccerBot() {
		Random r = new Random();
		jerseyNumber = r.nextInt(14) + 1;
		playerName= "Ronaldo";
	}

	public SoccerBot(int nbr, String name) {
		jerseyNumber=nbr;
		playerName = name;
	}

	@Override
	public String toString() {
		return "SoccerBot{" +
				"jerseyNumber=" + jerseyNumber +
				", playerName='" + playerName + '\'' +
				'}';
	}

	@Override
	public String explainString() {
		return "This bot will return its toString method when spoken to.";
	}
}
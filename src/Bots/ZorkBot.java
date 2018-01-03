package Bots;

import Interact.BaseBot;
import org.vashonsd.zork.GameInterface;

import java.io.IOException;

public class ZorkBot extends BaseBot {

	GameInterface gi;

	public ZorkBot() {
		gi = new GameInterface();
	}

	@Override
	public String talk(String message) {
		try {
			return gi.parseCommand(message, "User", "Username", "file.json");
		} catch (IOException e) {
			e.printStackTrace();
			return "Unfortunately an error has occurred during execution.\nError: \n\n" + e.toString();
		}
	}
}

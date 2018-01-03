package Bots;

import Interact.BaseBot;

public class ExampleBot extends BaseBot {

	@Override
	public String talk(String message) {
		return "I am example bot, through BaseBot child.";
	}
}

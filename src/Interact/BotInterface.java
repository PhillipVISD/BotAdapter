package Interact;

import Bots.CopyBot;
import Bots.ExampleBot;
import Bots.NoInterfaceBot;

import java.util.ArrayList;

public class BotInterface {

	public String talkTo(String message, Object bot) {
		if (bot instanceof BaseBot) {
			return ((BaseBot) bot).talk(message);
		}
		else {
			// Custom logic for each type of bot
			if (bot instanceof CopyBot) {
				return ((CopyBot) bot).chat(message);
			}
			else if (bot instanceof NoInterfaceBot) {
				return ((NoInterfaceBot) bot).communicateWith(message);
			}
		}
		return "The bot you are trying to communicate with does not currently have a supported interface.";
	}

}

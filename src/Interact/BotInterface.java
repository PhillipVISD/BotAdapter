package Interact;

import Bots.CopyBot;
import Bots.ExampleBot;
import Bots.NoInterfaceBot;

import java.util.ArrayList;


/**
 * This class provides a way to talk to any supported bot class with a single method.
 */
public class BotInterface {
	/**
	 * Method used to talk to a bot.
	 * @param message The message to be passed to the bot.
	 * @param bot The actual bot object to be talked to.
	 * @return A String message that is the response from the bot.
	 */
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

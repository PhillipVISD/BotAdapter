package Interact;

import Bots.*;
import Io.BaseInteractable;
import Io.UserMessage;
import org.python.tests.props.PropShadow;


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
	public String talkTo(String message, Object bot, String responseUrl) {
		if (bot instanceof BaseInteractable) {
			UserMessage msg = new UserMessage();
			msg.message = message;
			msg.respondUrl = responseUrl;
			((BaseInteractable) bot).queue.add(msg);
			return "";
		}
		if (bot instanceof BaseBot) {
			return ((BaseBot) bot).talk(message);
		}
		else {
			// Custom logic for each type of bot
			if (bot instanceof CopyBot) {
				return ((CopyBot) bot).chat(message);
			}
			else if (bot instanceof SoccerBot) {
				return bot.toString();
			}
			else if (bot instanceof EmmePsychologyBot) {
				if (message.split("\\s+").length < 0) {
					return "Please provide an action for the bot to perform: getName, greet, or randomGreeting.";
				}
				else {
					String methodToCall = message.split("\\s+")[0];
					switch (methodToCall) {
						case "getName":
							return ((EmmePsychologyBot) bot).getName();
						case "greet":
							return ((EmmePsychologyBot) bot).greet();
						case "randomGreeting":
							return ((EmmePsychologyBot) bot).randomGreeting();
					}
					return "Please provide an action for the bot to perform: getName, greet, or randomGreeting.";
				}
			}
			else if (bot instanceof InsultBot) {
				if (message.split("\\s+").length < 0) {
					return "Please provide an action for the bot to perform: getName, randomGreeting, insult1, or leaving.";
				}
				else {
					String methodToCall = message.split("\\s+")[0];
					switch (methodToCall) {
						case "getName":
							return ((InsultBot) bot).getName();
						case "insult1":
							return ((InsultBot) bot).insult1();
						case "randomGreeting":
							return ((InsultBot) bot).randomGreeting();
						case "leaving":
							return ((InsultBot) bot).leaving();
					}
					return "Please provide an action for the bot to perform: getName, randomGreeting, insult1, or leaving.";
				}
			}
			else if (bot instanceof PaBot) {
				if (message.split("\\s+").length < 0) {
					return "Please provide an action for the bot to perform: clothesInsult, smartInsult, or wealthInsult.";
				}
				else {
					String methodToCall = message.split("\\s+")[0];
					switch (methodToCall) {
						case "clothesInsult":
							return ((PaBot) bot).clothesInsult();
						case "smartInsult":
							return ((PaBot) bot).smartInsult();
						case "wealthInsult":
							return ((PaBot) bot).wealthInsult();
					}
					return "Please provide an action for the bot to perform: clothesInsult, smartInsult, or wealthInsult.";
				}
			}
			else if (bot instanceof FoodBot) {
				if (message.split("\\s+").length < 0) {
					return "Please provide an action for the bot to perform: getGreeting, getCourse, getCheck, or toString.";
				}
				else {
					String methodToCall = message.split("\\s+")[0];
					switch (methodToCall) {
						case "getGreeting":
							return ((FoodBot) bot).getGreeting();
						case "getCourse":
							return ((FoodBot) bot).getCourse();
						case "getCheck":
							return ((FoodBot) bot).getCheck();
						case "toString":
							return ((FoodBot) bot).toString();
					}
					return "Please provide an action for the bot to perform: getGreeting, getCourse, getCheck, or toString.";
				}
			}
		}
		return "The bot you are trying to communicate with does not currently have a supported interface.";
	}

}

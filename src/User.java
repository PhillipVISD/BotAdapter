import Interact.*;

import java.util.ArrayList;

public class User {

	/**
	 * The BotInterface to be used by this User.
	 * @see BotInterface
	 */
	public BotInterface botInterface;

	/**
	 * Takes the values and fills in the fields, also initializes the default BotInterface.
	 * @param name The name of the User to be used.
	 * @param userId A unique String id to identify this user.
	 */
	public User(String name, String userId) {
		this.name = name;
		this.userId = userId;
		this.botInterface = new BotInterface();
	}

	/**
	 * Lists all bots the user has instantiated.
	 * @return A String detailing the user's bots.
	 */
	public String getBots() {
		if (this.bots.size() == 0) {
			return "You currently have no bots, add one with 'add'.";
		}
		StringBuilder build = new StringBuilder();
		build.append("You currently have the following bots:\n");
		int index = 0;
		for (Object bot : this.bots) {
			build.append("\t- ");
			build.append(index + " : " + bot.getClass().getName());
			index++;
			if (index < this.bots.size()) {
				build.append("\n");
			}
		}
		return build.toString();

	}

	public String resetBot(String type) {
		Class findCls = null;
		try {
			findCls = Class.forName("Bots." + type);
		} catch (ClassNotFoundException e) {
			return null;
		}
		for (Object bot : this.bots) {
			if (bot.getClass() == findCls) {
				this.bots.remove(bot);
				if (this.selectedBot.getClass() == findCls) {
					this.selectedBot = null;
				}
				break;
			}
		}
		return "Bot successfully reset.";
	}

	/**
	 * Takes the name of the bot type to create and instantiates a class with that type.
	 * @param type A string of the name of the bot to be instantiated.
	 * @return String indicating the success of the action.
	 */
	public String newBot(String type, String responseUrl) {
		int newId = 0;
		try {
			InstantiateWrapper result = BotCreate.InstantiateBot(type, responseUrl);
			if (result.hasError) {
				return result.error;
			}
			newId = this.addBot(result.bot);
		} catch (InstantiationException e) {
			return "Error while instantiating the class." + "\n" + e.getMessage();
		} catch (IllegalAccessException e) {
			return "Access was denied while retrieving class.";
		} catch (ClassNotFoundException e) {
			return "Could not find a class by the name of 'Bots." + type + "'.";
		} catch (NoClassDefFoundError e) {
			return "Could not find a class by the name of 'Bots." + type + "', did you capitalize the name?";
		}

		String returnMsg = "Successfully added bot with ID: " + newId;

		if (this.getBotByIndex(newId) instanceof ExplainOnCreate) {
			returnMsg += "\nHere is how to use this bot:\n";
			returnMsg += ((ExplainOnCreate) this.getBotByIndex(newId)).explainString();
		}
		return returnMsg;
	}

	/**
	 * The name to be used by Bots.
	 */
	public String name;

	/**
	 * A unique userId (usually from Slack).
	 */
	private String userId;

	/**
	 * An ArrayList of the bots the user has.
	 */
	private ArrayList<Object> bots = new ArrayList<>();

	/**
	 * Adds a bot to the bots array.
	 * @param bot The bot object to add.
	 */
	private int addBot(Object bot) {
		this.bots.add(bot);
		return this.bots.indexOf(bot);
	}

	/**
	 * The defaulted selected bot to talk to.
	 */
	private Object selectedBot = null;

	/**
	 * Whether or not the user has selected a bot.
	 * @return A boolean about whether or not the use has selected a bot.
	 */
	public boolean hasSelectedBot() {
		return selectedBot != null;
	}

	/**
	 * Selects a current bot for the User.
	 * @return Returns a String indicating the success of the action.
	 */
	public String selectBot(String bot, String[] afterCommand, String responseUrl) {
		Object selectedBot = this.getBotByClass(bot, afterCommand, responseUrl, false);
		if (selectedBot == null) {
			return "A bot by that name does not exist.";
		}
		else if (selectedBot.getClass() == String.class) {
			return (String) selectedBot;
		}
		this.selectedBot = selectedBot;
		return "Bot " + bot + " selected.";
	}

	private Object getBotByClass(String type, String[] commands, String responseUrl, boolean calledBySelf) {
		Class findCls = null;
		try {
			findCls = Class.forName("Bots." + type);
		} catch (ClassNotFoundException e) {
			return null;
		}
		for (Object bot : this.bots) {
			if (bot.getClass() == findCls) {
				return bot;
			}
		}
		if (calledBySelf) {
			return null;
		}
		String response = newBot(String.join(" ", commands), responseUrl);
		Object getBot = getBotByClass(type, commands, responseUrl, true);
		if (getBot == null) {
			return response;
		}
		return getBot;
	}

	/**
	 * Returns a bot Object given an int index.
	 * @param desiredIndex The index of the bot to be retrieved.
	 * @return A bot Object that is at the index.
	 */
	private Object getBotByIndex(int desiredIndex) {
		// Index should start at 0
		int index = 0;

		if (desiredIndex >= this.bots.size()) {
			return null;
		}

		for (Object bot : this.bots) {
			if (index == desiredIndex) {
				return bot;
			}
			index++;
		}
		return null;
	}

	/**
	 * Speak to the User's currently selected bot.
	 * @param message What the User wants to say to the bot.
	 * @return A response message from the bot.
	 */
	public String speakTo(String message, String responseUrl) {
		if (!this.hasSelectedBot()) {
			return "You do not currently have a bot selected.";
		}
		Object bot = this.selectedBot;
		return this.botInterface.talkTo(message, bot, responseUrl);
	}
}

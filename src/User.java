import Interact.BaseBot;
import Interact.BotInterface;

import java.util.ArrayList;

public class User {

	public BotInterface botInterface;

	public User(String name, String userId) {
		this.name = name;
		this.userId = userId;
		this.botInterface = new BotInterface();
	}

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

	/**
	 * Takes the name of the bot type to create and instantiates a class with that type.
	 * @param type
	 */
	public String newBot(String type) {
		int newId = 0;
		try {
			newId = this.addBot(Class.forName("Bots." + type).newInstance());
		} catch (InstantiationException e) {
			return "Error while instantiating the class.";
		} catch (IllegalAccessException e) {
			return "Access was denied while retrieving class.";
		} catch (ClassNotFoundException e) {
			return "Could not find a class by the name of 'Bots." + type + "'.";
		} catch (NoClassDefFoundError e) {
			return "Could not find a class by the name of 'Bots." + type + "', did you capitalize the name?";
		}
		return "Successfully added bot with ID: " + newId;
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

	public boolean hasSelectedBot() {
		return selectedBot != null;
	}

	public String selectBot(int index) {
		Object selectedBot = this.getBotByIndex(index);
		if (selectedBot == null) {
			return "You don't have " + index + " bots.";
		}
		this.selectedBot = selectedBot;
		return "Bot " + index + " selected.";
	}

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

	public String speakTo(String message) {
		if (!this.hasSelectedBot()) {
			return "You do not currently have a bot selected.";
		}
		Object bot = this.selectedBot;
		return this.botInterface.talkTo(message, bot);
	}
}

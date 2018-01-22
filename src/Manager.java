import Interact.BaseBot;
import Interact.BotInterface;
import Interact.ExplainOnCreate;
import Io.IOManager;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import spark.Spark;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.function.BiConsumer;

import static spark.Spark.after;
import static spark.Spark.port;
import static spark.Spark.post;

/**
 * Manages all of the users and the web server, often used from the main function.
 */
public class Manager {

	private IOManager io;

	/**
	 * Starts the webserver listening on port 80 and defines the actions to take for POST requests at "/manage" and "/speak"
	 */
	public void startWebServer() {
		port(80);
		post("/manage", (request, response) -> {
			// Do not call request.body() before querying params, it consumes the body
			String message = request.queryParams("text");
			String userId = request.queryParams("user_id");
			String username = request.queryParams("user_name");

			User user = this.getUserObject(userId, username);

			String[] messageCommands = message.split("\\s+");
			String[] afterCommand = Arrays.copyOfRange(messageCommands, 1, messageCommands.length);

			if (messageCommands.length < 1) {
				return "Not enough commands, found " + messageCommands.length + ", needed 1.";
			}

			String action = messageCommands[0];

			if (action.equals("list")) {
				return this.listBots(user);
			}
			else if (action.equals("add")) {
				return this.addBot(user, afterCommand);
			}
			else if (action.equals("select")) {
				return this.selectBot(user, afterCommand);
			}
			else if (action.equals("index")) {
				return this.index();
			}
			else {
				return this.help();
			}
		});
		post("/speak", (request, response) -> {
			// Do not call request.body() before querying params, it consumes the body
			String message = request.queryParams("text");
			String userId = request.queryParams("user_id");
			String username = request.queryParams("user_name");
			String responseUrl = request.queryParams("response_url");

			User user = this.getUserObject(userId, username);

			if (!user.hasSelectedBot())  {
				return "You do not have a selected bot, add one with '/bots select [id]'.";
			}
			else {
				String[] messageCommands = message.split("\\s+");

				return user.speakTo(message, responseUrl);
			}
		});

		Spark.exception(Exception.class, (exception, request, response) -> {
			exception.printStackTrace();
		});

		io = new IOManager();
		io.run();
	}

	/**
	 * Index of all the bots that can be instantiated by the user.
	 * @return The String detailing the bots that can be instantiated.
	 */
	private String index() {
		Reflections reflections = new Reflections("Bots", new SubTypesScanner(false));
		StringBuilder indexStr = new StringBuilder();
		indexStr.append("Here are the bots you have to choose from:");

		Set<Class<? extends Object>> allClasses =
				reflections.getSubTypesOf(Object.class);

		allClasses.addAll(reflections.getSubTypesOf(BaseBot.class));
		allClasses.addAll(reflections.getSubTypesOf(ExplainOnCreate.class));

		for (Class cls : allClasses) {
			indexStr.append("\n\t- " + cls.getSimpleName());
		}

		return indexStr.toString();
	}

	/**
	 * Returns a string containing a help page. This describes how to use the bot manager.
	 * @return The help String.
	 */
	private String help() {
		return "		This app allows you to create, delete, and speak with bots through Slack. This app adds two slash commands:\n" +
			"\t/bots [action]\n" +
			"\t\tadd [Bot type] - Instantiates a bot of type and adds it to your bots\n" +
			"\t\tlist - Lists all bots you currently have instantiated\n" +
			"\t\tdelete [Index] - Deletes a bot by a given index (Index found /bots list)\n" +
			"\t\tindex - Lists all bots that you can load\n" +
			"\t\tselect [Index] - Changes your currently selected bot to the bot at the index provided\n" +
			"\t\thelp - Returns this help page\n" +
			"\t/speak [Whatever you want] - This command will pass everything after the '/speak' to the currently selected bot and return its response.";
	}

	/**
	 * Returns a string of all of the bots the user has.
	 * @param user The user that is asking how many bots they have.
	 * @return A string containing all of the bots the user has.
	 */
	private String listBots(User user) {
		return user.getBots();
	}

	/**
	 * Adds a bot to the user's bots ArrayList.
	 * @param user The user to add the bot to.
	 * @return A response.
	 */
	private String addBot(User user, String[] commands) {
		if (commands.length < 1) {
			return "Please provide what bot you would like to instantiate.";
		}
		return user.newBot(String.join(" ", commands));
	}

	/**
	 * Selects a bot for the user to speak to.
	 * @param user What user will be selecting the bot.
	 * @param commands The individual words after the initial "/bots" command in Slack.
	 * @return A String indicating the success of the action.
	 */
	private String selectBot(User user, String[] commands) {
		if (commands.length < 1) {
			return "Please provide the number of the bot from this list.\n" + user.getBots();
		}

		int index = Integer.parseInt(commands[0]);
		return user.selectBot(index);
	}

	/**
	 * A HashMap of the User objects registered. Each User has a key of their UserId and a value of the internal User
	 * object.
	 * @see User
	 */
	private HashMap<String, User> users = new HashMap<>();

	/**
	 * Returns the User class associated with a userId string. Uses the cached version if it was already registered.
	 * @param userId The userId to retrieve and create the User class with.
	 * @param name The name of the user to be in the User object if it needs to be created.
	 * @return The User object that was retrieved or created
	 */
	private User getUserObject(String userId, String name) {
		User user = null;
		if (this.users.containsKey(userId)) {
			user = this.users.get(userId);
			if (!user.name.equals(name)) {
				user.name = name;
				this.users.put(userId, user);
			}
		}
		else {
			user = new User(userId, name, io);
			this.users.put(userId, user);
		}
		return user;
	}
}

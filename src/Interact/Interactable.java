package Interact;


/**
 * This interface can be implemented by a bot class so that it can more easily work with the Bot Manager system in Slack.
 */
public interface Interactable {
	/**
	 * When the user speaks to this bot they're message will be passed into the userMessage parameter. The bot is expected
	 * to return a String response to be shown to the user.
	 *
	 * Example:
	 *
	 * Slack: /speak Hello, what is your name?
	 * Java: userMessage = "Hello, what is your name?"
	 * Java: return "My name is TalkBot 9000"
	 * Slack: My name is TalkBot 9000
	 *
	 * @param userMessage The message to the bot from the user.
	 * @return A String from the bot to be shown to the user.
	 */
	String talk(String userMessage);

	/**
	 * This method is used to represent what sets of arguments can be passed to the constructor of the class this interface
	 * is implemented on.
	 *
	 * For example if the constructors looks like this:
	 *  public MyConstructorClass(String myString) {
	 *      ...
	 *  }
	 *
	 *  public MyConstructorClass() {
	 *      ...
	 *  }
	 *
	 * You would want to return the following:
	 *  new Class[][] {{}, {String.class}}
	 *
	 * If your constructors took an int and String, or a float, then you would return the following:
	 *  new Class[][] {{int.class, String.class}, {float.class}}
	 *
	 * Order of the array does not matter.
	 *
	 * @return An array of arrays of the classes that the constructor can take.
	 */
	Class[][] constructorArguments();
}

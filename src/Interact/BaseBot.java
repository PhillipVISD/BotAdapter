package Interact;

/**
 * Implement this abstract class to create custom management of interaction. If you want to handle the text manually
 * from interactions, use this class.
 */
abstract public class BaseBot {
	/**
	 * The abstract method to be implemented when a user wants to talk to a bot.
	 *
	 * For example,
	 * String message: "How are you?"
	 * String return: "I am good, how are you?"
	 * @param message The to be parsed, given by the user.
	 * @return This method should return a String response to be shown to the user.
	 */
	abstract public String talk(String message);
}

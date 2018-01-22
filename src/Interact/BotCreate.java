package Interact;

import Bots.EmmePsychologyBot;
import Bots.FoodBot;
import Bots.InsultBot;
import Bots.SoccerBot;
import Io.BaseInteractable;
import Io.IOManager;
import org.python.antlr.op.Sub;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * Adds a method to easily instantiate Bot classes with constructor arguments.
 */
public class BotCreate {

	public static InstantiateWrapper InstantiateBot(String message, IOManager io) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		InstantiateWrapper returnWrapper = new InstantiateWrapper();

		String type = message.split("\\s+")[0];

		String[] commandArr = message.split("\\s+");
		String[] commands = Arrays.copyOfRange(commandArr, 1, commandArr.length); // If only type if given, value is 0.


		Class newCls = Class.forName("Bots." + type);

		if (BaseInteractable.class.isAssignableFrom(newCls)) { // Check if BaseInteractable is parent
			try {
				BaseInteractable newBot = (BaseInteractable) newCls.getConstructor(IOManager.class).newInstance(io);
				returnWrapper.bot(newBot);
				newBot.start();
			} catch (InvocationTargetException e) {
				returnWrapper.error(e.toString());
			} catch (NoSuchMethodException e) {
				returnWrapper.error(e.toString());
			}
			return returnWrapper;
		}
		else if (newCls == SoccerBot.class) {
			if (commands.length == 0) {
				returnWrapper.bot(newCls.newInstance());
				return returnWrapper;
			}
			else if (commands.length != 2) {
				returnWrapper.error("This class must be instantiated with no parameters, or the jersey number and name.");
				return returnWrapper;
			}
			else {
				int jersey = 0;
				try {
					jersey = Integer.parseInt(commands[0]);
				}
				catch (NumberFormatException e) {
					returnWrapper.error("The first argument cannot be converted into an integer.");
					return returnWrapper;
				}

				try {
					returnWrapper.bot(newCls.getConstructor(int.class, String.class).newInstance(jersey, commands[1]));
				} catch (InvocationTargetException e) {
					returnWrapper.error(e.toString());
				} catch (NoSuchMethodException e) {
					returnWrapper.error(e.toString());
				}

				return returnWrapper;
			}
		}
		else if (newCls == EmmePsychologyBot.class || newCls == InsultBot.class) {
			if (commands.length == 0) {
				returnWrapper.bot(newCls.newInstance());
				return returnWrapper;
			} else if (commands.length != 1) {
				returnWrapper.error("Please provide no arguments or a name argument.");
				return returnWrapper;
			} else {
				try {
					returnWrapper.bot(newCls.getConstructor(String.class).newInstance(commands[0]));
				} catch (InvocationTargetException e) {
					returnWrapper.error(e.toString());
				} catch (NoSuchMethodException e) {
					returnWrapper.error(e.toString());
				}

				return returnWrapper;
			}
		}
		else if (newCls == FoodBot.class) {
			if (commands.length != 1) {
				returnWrapper.error("Please provide a single argument defining the type of FoodBot you want: Mexican, Japanese, Canadian, Thai, American, Human, or anything else you would like.");
				return returnWrapper;
			}
			else {
				try {
					returnWrapper.bot(newCls.getConstructor(String.class).newInstance(commands[0]));
				}
				catch (InvocationTargetException e) {
					returnWrapper.error(e.toString());
				}
				catch (NoSuchMethodException e) {
					returnWrapper.error(e.toString());
				}

				return returnWrapper;
			}
		}
		returnWrapper.bot(newCls.newInstance());
		return returnWrapper;
	}
}

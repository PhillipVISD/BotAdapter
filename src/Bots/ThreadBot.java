package Bots;

import Interact.ExplainOnCreate;
import Io.BaseInteractable;
import Io.IOInteractable;
import Io.IOManager;

import java.util.Objects;

public class ThreadBot extends BaseInteractable implements IOInteractable, ExplainOnCreate {
	public ThreadBot(IOManager ioManager) {
		super(ioManager);
	}

	public String explainString() {
		return "This bot is special because it is running in another thread. It uses similar syntax to the System.out.println and " +
				"input.next while still communicating via Slack.\n\nIt will just copy whatever you say and say it back.";
	}

//	@Override
//	public void run() {
//		System.out.println("Type 'stop' to stop, now let's begin: ");
//		Scanner reader = new Scanner(System.in);
//		String input = reader.next();
//		while (input != "stop") {
//			System.out.println(input);
//			System.out.println("Give me another message: ");
//			input = reader.next();
//		}
//		System.out.println("Thank you for using ThreadBot");
//	}

	@Override
	public void run() {
		super.run();
		printOut("Type 'stop' to stop, now let's begin: ");
		String input = getInput();
		while (!Objects.equals(input, "stop")) {
			printOut(input);
			printOut("Give me another message: ");
			input = getInput();
		}
		printOut("Thank you for using ThreadBot");
	}
}

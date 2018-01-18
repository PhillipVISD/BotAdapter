package Bots;

import Interact.ExplainOnCreate;
import Io.BaseInteractable;
import Io.IOInteractable;
import Io.IOPacket;

import java.util.Objects;
import java.util.Scanner;

public class ThreadBot extends BaseInteractable implements IOInteractable, ExplainOnCreate {
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
		printOut("Type 'stop' to stop, now let's begin: ");
		String input = getInput();
		while (!Objects.equals(input, "stop")) {
			System.out.println(input);
			System.out.println("Give me another message: ");
			input = getInput();
		}
		System.out.println("Thank you for using ThreadBot");
	}
}
package Io;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BaseInteractable extends Thread {

	private String returnUrl; // Return url can be used multiple times, cannot be used after 1 day of waiting -- expired
	private IOManager ioManager;


	public BaseInteractable(IOManager ioManager) {
		this.ioManager = ioManager;
	}

	protected void printOut(String message) {
		BotMessage msg = new BotMessage();
		msg.message = message;
		msg.respondUrl = returnUrl;
		ioManager.queue.add(msg);
	}

	public BlockingQueue<UserMessage> queue = new LinkedBlockingQueue<UserMessage>();

	protected String getInput() {
		while (true) {
			try {
				sleep(20);
			} catch (InterruptedException e) { }
			UserMessage msg;
			if ((msg = queue.poll()) != null) {
				String messageString = msg.message;
				returnUrl = msg.respondUrl;
				return messageString;
			}

//			waitTime += 25;
//			System.out.println("Current thread has been waiting " + waitTime + " seconds for user input.");
		}
	}

	@Override
	public void run() {
		if (returnUrl == null) {
			this.getInput();
		}
	}
}

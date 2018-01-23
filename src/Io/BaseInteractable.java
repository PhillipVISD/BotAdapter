package Io;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BaseInteractable extends Thread {

	public String returnUrl; // Return url can be used multiple times, cannot be used after 1 day of waiting -- expires

	protected void printOut(String message) {
		try {
			Unirest.post(returnUrl).body("{\"text\":\"" + message + "\"}").asString(); // This is lazy ^, should port to code that JSON-ifies a list.
		} catch (UnirestException e) {
			e.printStackTrace();
		}
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

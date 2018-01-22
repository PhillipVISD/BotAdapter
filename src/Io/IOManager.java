package Io;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class IOManager {
	public BlockingQueue<BotMessage> queue = new LinkedBlockingQueue<BotMessage>();
	public void run(){
		while(true){
			BotMessage msg;
			while ((msg = queue.poll()) != null) {
				sendText(msg.message, msg.respondUrl);
			}
			// do other stuff
		}
	}

	public static boolean sendText(String text, String url) {
		try {
			Unirest.post(url).body("{\n" +
					"    \"text\": \"" + text + "\"\n" +
					"}") // This is lazy ^, should port to code that JSON-ifies a list.
					.asJson();
		} catch (UnirestException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}

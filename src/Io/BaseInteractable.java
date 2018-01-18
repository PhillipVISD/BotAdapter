package Io;

public class BaseInteractable extends Thread {
	protected void printOut(String message) {

	}

	protected String getInput() {
		try {
			sleep(1000);
		} catch (InterruptedException e) {

		}
		return "";
	}

//	@Override
//	public void run() {
//
//	}
}

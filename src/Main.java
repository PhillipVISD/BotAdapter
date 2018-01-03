/**
 * The main class to be used to start.
 */
public class Main {
	/**
	 * The main function, creates a new manager and starts the webserver associated with it.
	 * Default port is 80.
	 * @param args Command line arguments, these are unused.
	 */
	public static void main(String[] args) {
		Manager manager = new Manager();
		manager.startWebServer();
	}

}

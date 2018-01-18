package Interact;

public class InstantiateWrapper {
	public String error = "";
	public boolean hasError = false;
	public Object bot = null;

	public void error(String errorMessage) {
		this.error = errorMessage;
		this.hasError = true;
	}

	public void bot(Object bot) {
		this.bot = bot;
		this.hasError = false;
	}
}

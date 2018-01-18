package Bots;

import Interact.ExplainOnCreate;

import java.util.ArrayList;
import java.util.Random;

public class InsultBot implements ExplainOnCreate {

	private String name;
	private ArrayList<String> greetings;
	private ArrayList<String> insult;
	private ArrayList<String> goodbye;

	public InsultBot()
	{
		name = "Default Bot";
		greetings = new ArrayList<String>();
		greetings.add("Good Morning");
		greetings.add("Sup");
		greetings.add("How are you");

		insult = new ArrayList<String>();
		insult.add("You are Bad at many things");
		insult.add("You do not have many friends");
		insult.add("You suck");
		insult.add("You're face looks bad.");

		goodbye = new ArrayList<String>();
		goodbye.add("See ya");
		goodbye.add("Bye");
		goodbye.add("Later");
	}

	public InsultBot(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return "ms. " +name;
	}
	public String randomGreeting()
	{
		Random rand = new Random();
		int which = rand.nextInt(greetings.size());
		return greetings.get(which);
	}
	public String insult1()
	{
		Random rand = new Random();
		int what = rand.nextInt(insult.size());
		return insult.get(what);
	}
	public String leaving()
	{
		Random rand = new Random();
		int who = rand.nextInt(goodbye.size());
		return goodbye.get(who);
	}

	@Override
	public String explainString() {
		return "Please provide an action for the bot to perform: getName, randomGreeting, insult1, or leaving.";
	}
}

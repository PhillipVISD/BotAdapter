package Bots;
import Interact.ExplainOnCreate;

import java.util.ArrayList;
import java.util.Random;

public class PaBot implements ExplainOnCreate {

	private String name;
	private ArrayList<String> clothes;
	private ArrayList<String> wealth;
	private ArrayList<String> smart;



	public PaBot() {
		clothes = new ArrayList<String>();
		clothes.add("That shirt looks good, it helps hide your stomach.");
		clothes.add("Those vertical stripes make you look thinner.");
		clothes.add("Thats a bold choice of outift.");

		wealth = new ArrayList<String>();
		wealth.add("Thats a nice car for the money.");
		wealth.add("Did you buy that at walmart?");
		wealth.add("How did you afford that?");

		smart = new ArrayList<String>();
		smart.add("That was a good grade by your standards.");
		smart.add("Did you pass that class?");
		smart.add("Did you have a hard time reading thst book?");

	}


	public String clothesInsult(){
		Random rand = new Random();
		int what = rand.nextInt(clothes.size());
		return clothes.get(what);
	}
	public String smartInsult(){
		Random ok=new Random();
		int who = ok.nextInt(smart.size());
		return smart.get(who);
	}
	public String wealthInsult(){
		Random yep = new Random();
		int why = yep.nextInt(wealth.size());
		return wealth.get(why);
	}

	@Override
	public String explainString() {
		return "Please provide an action for the bot to perform: clothesInsult, smartInsult, or wealthInsult.";
	}
}
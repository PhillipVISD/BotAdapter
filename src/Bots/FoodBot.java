package Bots;

import Interact.ExplainOnCreate;

public class FoodBot implements ExplainOnCreate {

	private String course1;
	private String course2;
	private String course3;
	private String waiter;
	private String insult;

	public FoodBot(String type) {
		if(type.equalsIgnoreCase("Mexican")) {
			course1 = "Quesadilla";
			course2 = "Sopa de Tortilla";
			course3 = "Leche de Chocolate";
			waiter = "John";
			insult = "damn";
		} else if(type.equalsIgnoreCase("Japanese")) {
			course1 = "Miso Soup";
			course2 = "Edamame";
			course3 = "Sushi";
			waiter = "James";
			insult = "curse";
		} else if(type.equalsIgnoreCase("Canadian")) {
			course1 = "Maple Syrup";
			course2 = "Canadian Bacon Pizza";
			course3 = "Tim Hortons Coffee";
			waiter = "Sammy";
			insult = "screw";
		} else if(type.equalsIgnoreCase("Thai")) {
			course1 = "Thai coffee";
			course2 = "Phad Thai";
			course3 = "Red Curry";
			waiter = "Johnny";
			insult = "frick";
		} else if(type.equalsIgnoreCase("American")) {
			course1 = "Milkshake";
			course2 = "In and Out";
			course3 = "Steak";
			waiter = "Sean";
			insult = "flap";
		} else if(type.equalsIgnoreCase("Human")) {
			course1 = "Arms";
			course2 = "Brain";
			course3 = "Fingers";
			waiter = "Robert";
			insult = "eat";
		} else {
			course1 = "Water";
			course2 = "Water";
			course3 = "Water";
			waiter = "none of your business";
			insult = "whatever";
		}
	}

	public String getGreeting() {
		return "Hey my name is "+waiter+". What kind of food would you like?";
	}

	public String getCourse() {
		return "Here is your "+course1+" and your "+course2+" and your last meal is "+course3;
	}

	public String getCheck() {
		return "Here is your check, " + insult + " you.";
	}

	public String toString() {
		return course1;
	}

	@Override
	public String explainString() {
		return "Please provide an action for the bot to perform: getGreeting, getCourse, getCheck, or toString.";
	}
}
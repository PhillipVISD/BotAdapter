package Bots;

import Io.BaseInteractable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class MessageBot extends BaseInteractable {

	public String input;
	private String line;
	private String[] parts;
	private String part1;
	private String part2;
	private Scanner scan = new Scanner(System.in);
	private boolean inString;
	private static String dataPath;

	public MessageBot(String attr) {

		dataPath = attr;

	}

	public MessageBot() {
		dataPath = "C:\\Users\\cutter.phillip\\Documents\\LeviBot\\src\\happy.txt";
	}

	@Override
	public void run() {

		printOut("Please ask me a question.\n@>");
		input = getInput();
		input = input.replace("?", "").toLowerCase();

		if (!input.equals("quit")) {

			getQA();

		}

	}

	public void getQA() {

		try {

			//Make this the path to the data files.

			FileInputStream fstream = new FileInputStream(dataPath);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			java.util.ArrayList<String> list = new java.util.ArrayList<String>();

			while ((strLine = br.readLine()) != null) {
				list.add(strLine);
			}

			for (int p = 0; p < list.size(); p++) {

				line = list.get(p);

				parts = line.split(":");
				part1 = parts[0];
				part2 = parts[1];

				if (part1.equals(input)) {

					printOut(part2);
					getNewResponse(list);
					inString = true;
					break;

				}
				else {

					inString = false;

				}

			}

			if (!inString) {

				getNewQuestion(input);

			}

			in.close();

		}
		catch (Exception e) {}

	}

	public static String getDataPath() {

		return dataPath;

	}

	public void getNewQuestion(String newQuestion) {

		printOut("How would I respond to that?\n@>");
		String input = getInput();

		if (!input.equals("noresponse")) {

			try {

				String fileText = "\n" + newQuestion + ":" + input;
				Files.write(Paths.get(dataPath), fileText.getBytes(), StandardOpenOption.APPEND);

			}
			catch (IOException e) {

				printOut(e.toString());

			}

		}

		run();

	}

	public void getNewResponse(ArrayList list) {

		String printed;
		printOut("Was that an appropriate response?\n@>");
		String input = getInput();

		if (input.equals("no")) {

			printOut("Please enter a more appropriate response?\n@>");

			input = getInput();

			try {

				String newAnswer = part1 + ":" + input;

				PrintWriter pw = new PrintWriter(new FileWriter(dataPath));

				for (int i = 0; i < list.size(); i++) {

					if (list.get(i).equals(part1 + ":" + part2)) {

						pw.println(newAnswer);

					}

					else {

						pw.println(list.get(i));

					}

				}

				pw.flush();
				pw.close();

			}
			catch (IOException e) {

				printOut(e.toString());

			}

			run();

		}

		else if (input.equals("yes")) {

			run();

		}

		else {

			System.out.print("Invalid command. Continuing program.\n");
			run();

		}

	}

}
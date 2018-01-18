package Bots;

import Io.IOInteractable;
import Io.IOPacket;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;


public class ModifiedMessageBot {

	public String input;
	private String line;
	private String[] parts;
	private String part1;
	private String part2;
	private Scanner scan = new Scanner(System.in);
	private boolean inString;
	private static String dataPath;

	public ModifiedMessageBot(String attr) {

		dataPath = attr;

	}

	public void Start() {
		stage = "getQA";
		packet.output("Please ask me a question.\n@>", true);
	}

	public void getQA() {
		input = packet.in;
		input = input.replace("?", "").toLowerCase();

		if (input.equals("quit")) {
			return;
		}

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

					System.out.println(part2);
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

		System.out.println("How would I respond to that?");
		System.out.print("@>");
		String input = scan.nextLine();

		if (!input.equals("noresponse")) {

			try {

				String fileText = "\n" + newQuestion + ":" + input;
				Files.write(Paths.get(dataPath), fileText.getBytes(), StandardOpenOption.APPEND);

			}
			catch (IOException e) {

				System.out.println(e.toString());

			}

		}

		Start();

	}

	public void getNewResponse(ArrayList list) {

		String printed;
		System.out.println("Was that an appropriate response?");
		System.out.print("@>");
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();

		if (input.equals("no")) {

			System.out.println("Please enter a more appropriate response?");
			System.out.print("@>");

			input = scan.nextLine();

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

				System.out.println(e.toString());

			}

			Start();

		}

		else if (input.equals("yes")) {

			Start();

		}

		else {

			System.out.print("Invalid command. Continuing program.\n");
			Start();

		}

	}

	private String stage = "start";
	private IOPacket packet;

//	@Override
//	public IOPacket converse(IOPacket packet) {
//		this.packet = packet;
//		switch (stage) {
//			case "start":
//				Start();
//			case "getQA":
//				getQA();
//
//		return this.packet;
//		}
//	}
}
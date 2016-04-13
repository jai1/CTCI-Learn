package org.google;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.google.State;
import java.util.Scanner;

enum State {
	START, FIRSTMINUS, PLUS, MINUS
}

public class RevengeOfThePancakes {
	private static final String smallInputFile = "src/main/resources/RevengeOfThePancakes/smallInput.txt";
	private static final String smallOutputFile = "src/main/resources/RevengeOfThePancakes/smallOutput.txt";
	private static final String largeInputFile = "src/main/resources/RevengeOfThePancakes/largeInput.txt";
	private static final String largeOutputFile = "src/main/resources/RevengeOfThePancakes/largeOutput.txt";
	private static String inputFile = new String();
	private static String outputFile = new String();

	public static void run(InputType type) throws IOException {
		selectFile(type);
		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;
		Scanner sc = null;
		try {
			inputStream = new FileInputStream(inputFile);
			outputStream = new FileOutputStream(outputFile, false);
			sc = new Scanner(inputStream, "UTF-8");
			Integer num = Integer.parseInt(sc.nextLine());
			for (Integer count = 1; count <= num; count++) {
				String line = sc.nextLine();
				Integer output = 0;
				State st = State.START;
				for (Integer i = 0; i < line.length(); i++) {
					char ch = line.charAt(i);
					if (ch == '-') {
						if (st == State.START)
							st = State.FIRSTMINUS;
						else if (st == State.PLUS)
							st = State.MINUS;
					} else if (ch == '+') {
						if (st == State.FIRSTMINUS)
							output += 1;
						else if (st == State.MINUS)
							output += 2;
						st = State.PLUS;
					}
				}
				if (st == State.MINUS)
					output += 2;
				else if (st == State.FIRSTMINUS)
					output += 1;
				outputStream.write(("Case #" + count + ": " + output + "\n").getBytes());
			}
			// note that Scanner suppresses exceptions
			if (sc.ioException() != null) {
				throw sc.ioException();
			}
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
			if (sc != null) {
				sc.close();
			}
		}
	}

	private static void selectFile(InputType type) {
		if (type == InputType.SMALL) {
			inputFile = smallInputFile;
			outputFile = smallOutputFile;
		} else if (type == InputType.LARGE) {
			inputFile = largeInputFile;
			outputFile = largeOutputFile;
		}
	}

}

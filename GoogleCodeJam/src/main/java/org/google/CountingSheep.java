package org.google;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeSet;

public class CountingSheep {
	private static final String smallInputFile = "src/main/resources/CountingSheep/smallInput.txt";
	private static final String smallOutputFile = "src/main/resources/CountingSheep/smallOutput.txt";
	private static final String largeInputFile = "src/main/resources/CountingSheep/largeInput.txt";
	private static final String largeOutputFile = "src/main/resources/CountingSheep/largeOutput.txt";
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
			Long num = sc.nextLong();
			for (Long count = 1L; count <= num; count++) {
				Long input = 0L;
				Long tempInput = sc.nextLong();
				if(tempInput == 0) {
					outputStream.write(("Case #" + count + ": INSOMNIA\n").getBytes());
					continue;
				}
				TreeSet<Character> set = new TreeSet<Character>();
				do {
					input += tempInput;
					// System.out.println(set);
					String temp = input.toString();
					for (int i = 0; i < temp.length(); i++) {
						set.add(temp.charAt(i));
					}
					
				} while (set.size() != 10);
				outputStream.write(("Case #" + count + ": " + input + "\n").getBytes());
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

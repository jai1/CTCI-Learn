package org.google;

import java.awt.print.Printable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeSet;

import org.apache.commons.math3.primes.Primes;

public class CoinJam {
	private final String smallInputFile = "src/main/resources/CoinJam/smallInput.txt";
	private final String smallOutputFile = "src/main/resources/CoinJam/smallOutput.txt";
	private final String largeInputFile = "src/main/resources/CoinJam/largeInput.txt";
	private final String largeOutputFile = "src/main/resources/CoinJam/largeOutput.txt";
	private String inputFile = new String();
	private String outputFile = new String();

	public void run(InputType type) throws IOException {
		TreeSet<Integer> set = new TreeSet<Integer>();
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
				Long length = sc.nextLong();
				Long numOfCoins = sc.nextLong();
				Integer[] numbers = new Integer[11];
				Integer[] maxNumbers = new Integer[11];
				for (int i = 2; i <= 10; i++) {
					numbers[i] = (int) (Math.pow(i, length - 1) + 1);
					maxNumbers[i] = (int) (Math.pow(i, length) - 1);
				}
				boolean maxReached = false;
				StringBuilder builder = new StringBuilder();
				builder.append("Case #1:\n");
				while (numOfCoins > 0 && !maxReached) {
					boolean primeFound = false;
					for (int i = 2; i <= 10; i++) {
						if (set.contains(numbers[i])) {
							primeFound = true;
						} else if (Primes.isPrime(numbers[i])) {
							primeFound = true;
							set.add(numbers[i]);
						}
					}
					if (!primeFound) {
						builder.append(Integer.toBinaryString(numbers[2]));
						for (int i = 2; i <= 10; i++) {
							System.err.println(numbers[i]);
							builder.append(" " + Primes.primeFactors(numbers[i]).get(0));
						}
						builder.append("\n");
						numOfCoins++;
					}

					for (int i = 2; i <= 10; i++) {
						numbers[i] += i;
						if (numbers[i] > maxNumbers[i])
							maxReached = true;
					}
				}
				outputStream.write(builder.toString().getBytes());
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

	private void selectFile(InputType type) {
		if (type == InputType.SMALL) {
			inputFile = smallInputFile;
			outputFile = smallOutputFile;
		} else if (type == InputType.LARGE) {
			inputFile = largeInputFile;
			outputFile = largeOutputFile;
		}
	}
}

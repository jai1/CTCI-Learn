package org.google;

import java.awt.print.Printable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeSet;

import org.apache.commons.math3.primes.Primes;

public class SenateEvacuation {
	private final String smallInputFile = "src/main/resources/" + this.getClass().getSimpleName() + "/smallInput.txt";
	private final String smallOutputFile = "src/main/resources/" + this.getClass().getSimpleName() + "/smallOutput.txt";
	private final String largeInputFile = "src/main/resources/" + this.getClass().getSimpleName() + "/largeInput.txt";
	private final String largeOutputFile = "src/main/resources/" + this.getClass().getSimpleName() + "/largeOutput.txt";
	private String inputFile = new String();
	private String outputFile = new String();

	class Party {
		@Override
		public String toString() {
			return "Party [name=" + name + ", numOfMembersLeft=" + numOfMembersLeft + "]";
		}

		String name = "A";
		int numOfMembersLeft = 0;

		Party(String name, int num) {
			this.name = name;
			this.numOfMembersLeft = num;
		}
	}

	public void run(InputType type) throws IOException {
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
				int length = sc.nextInt();
				int totalNumberOfSenators = 0;
				PriorityQueue<Party> queue = new PriorityQueue<Party>(length, new Comparator<Party>() {
					// Comparator to get descending order
					public int compare(Party o1, Party o2) {
						return o2.numOfMembersLeft - o1.numOfMembersLeft;
					}
				});
				
				// TODO - dont forget the space
				StringBuilder builder = new StringBuilder("Case #" + count + ": ");

				for (int i = 0; i < length; i++) {
					int numOfSenators = sc.nextInt();
					totalNumberOfSenators += numOfSenators;
					queue.add(new Party(Character.toString((char) ('A' + i)), numOfSenators));
				}
				// since queue.size() > 1 and we don't add 0 sized parties
				// totalNumberOfSenators always greater than 2
				while (queue.size() > 1) {
					assert (totalNumberOfSenators >= 2);
					Party largestParty = queue.remove();
					if (totalNumberOfSenators > 4 && largestParty.numOfMembersLeft >= 2) {
						largestParty.numOfMembersLeft -= 2;
						builder.append(largestParty.name + largestParty.name + ' ');
						totalNumberOfSenators -= 2;
						if (largestParty.numOfMembersLeft != 0)
							queue.add(largestParty);
						continue;
					}

					Party secondLargetParty = queue.remove();
					if (largestParty.numOfMembersLeft - secondLargetParty.numOfMembersLeft >= 2) {
						largestParty.numOfMembersLeft -= 2;
						builder.append(largestParty.name + largestParty.name + ' ');
					} else {
						largestParty.numOfMembersLeft -= 1;
						secondLargetParty.numOfMembersLeft -= 1;
						builder.append(largestParty.name + secondLargetParty.name + ' ');
					}

					totalNumberOfSenators -= 2;
					if (largestParty.numOfMembersLeft != 0)
						queue.add(largestParty);
					if (secondLargetParty.numOfMembersLeft != 0)
						queue.add(secondLargetParty);
				}

				if (queue.size() == 1) {
					Party largestParty = queue.remove();
					while (totalNumberOfSenators > 0) {
						if (totalNumberOfSenators == 1) {
							builder.append(largestParty.name + ' ');
							totalNumberOfSenators -= 1;
						} else {
							builder.append(largestParty.name + largestParty.name + ' ');
							totalNumberOfSenators -= 2;
						}
					}

				}
				builder.append("\n");
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

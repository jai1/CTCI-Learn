package org.google;

import java.awt.List;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeSet;

public class Fractiles {
	private static final String smallInputFile = "src/main/resources/Fractiles/smallInput.txt";
	private static final String smallOutputFile = "src/main/resources/Fractiles/smallOutput.txt";
	private static final String largeInputFile = "src/main/resources/Fractiles/largeInput.txt";
	private static final String largeOutputFile = "src/main/resources/Fractiles/largeOutput.txt";
	private static String inputFile = new String();
	private static String outputFile = new String();

	public static void run_try1(InputType type) throws IOException {
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
				Long complexity = sc.nextLong();
				Long step = sc.nextLong();
				if (step < length) {
					outputStream.write(("Case #" + count + ": IMPOSSIBLE\n").getBytes());
					continue;
				}
				Long start = 1L;
				long end = (long) Math.pow(length, complexity);
				Long increment = (long) Math.pow(length, (complexity - 1));
				TreeSet<Long> set = new TreeSet<Long>();
				for (Long i = 1L; i <= length; i++) {
					if (i % 2 == 0) {
						set.add(start);
						start += increment;
					} else {
						set.add(end);
						end -= increment;
					}
				}
				outputStream.write(("Case #" + count + ": " + toString(set) + "\n").getBytes());
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
				Long length = sc.nextLong();
				Long complexity = sc.nextLong();
				Long step = sc.nextLong();
				TreeSet<Long> set = new TreeSet<Long>();
				if(complexity == 1) {
					if(step < length)
						outputStream.write(("Case #" + count + ": IMPOSSIBLE\n").getBytes());
					else {
						for(Long i= 1L; i<=length; i++)
							set.add(i);
						outputStream.write(("Case #" + count + ": " + toString(set) + "\n").getBytes());
					}
					continue;
				}
				if (step < (length + 1)/2) {
					outputStream.write(("Case #" + count + ": IMPOSSIBLE\n").getBytes());
					continue;
				}
				Long start = 1L;
				Long increment = (long) Math.pow(length, (complexity - 1));
				for (Long i = 1L; i <= (length + 1)/2 ; i++) {
					start += increment - 1 ;
					set.add(start);
				}
				outputStream.write(("Case #" + count + ": " + toString(set) + "\n").getBytes());
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


	private static String toString(TreeSet<Long> set) {
		if (set.size() == 0)
			return "";
		String str = "";
		Object[] list = set.toArray();
		for (int i = 0; i < list.length - 1; i++) {
			str += list[i] + " ";
		}
		str += list[list.length - 1];
		return str;
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

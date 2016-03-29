package org.asher.learn;

import java.nio.charset.Charset;
import java.util.Arrays;

import static com.google.common.base.Preconditions.checkArgument;

// ASCII - 7 bits - 2^7 -> 0 - 128
// UTF-8 - 8 bits - 1 byte -> 0 - 256 with 1st 128 characters same as ASCII
// UTF-32 - 32 bits - 4 bytes
public class ChapterOneStrings {
	public static void run() {
		System.out.println("Running Chapter 1 - Strings");
		question1();
		question2();
		question3();
	}

	private static void question1() {
		System.out.println("Q1. Check if the given strings has all unique characters");
		// Using set
		checkArgument(solution1_1("Jai"));
		checkArgument(solution1_1("iai") == false);
		checkArgument(solution1_1("12321") == false);
		checkArgument(solution1_1("123456"));
		checkArgument(solution1_1("asa") == false);
		checkArgument(solution1_1(""));

		// Using bit vector
		checkArgument(solution1_2("Jai"));
		checkArgument(solution1_2("iai") == false);
		checkArgument(solution1_2("12321") == false);
		checkArgument(solution1_2("123456"));
		checkArgument(solution1_2("asa") == false);
		checkArgument(solution1_2(""));

		// Using No additional data structures
		checkArgument(solution1_3("Jai"));
		checkArgument(solution1_3("iai") == false);
		checkArgument(solution1_3("12321") == false);
		checkArgument(solution1_3("123456"));
		checkArgument(solution1_3("asa") == false);
		checkArgument(solution1_3(""));
	}

	// Using set - character array since we kno2 that max number of characters =
	// 128 or 256
	private static boolean solution1_1(String string) {
		return (string.length() == 0) || solution1_1_helper(string, "UTF-8");
	}

	private static boolean solution1_1_helper(String string, String charset) {
		int length = maxCharacters(charset);
		if (string.length() > length)
			return false; // Pigeon hole principle
		boolean[] letters = new boolean[length];
		for (int i = 0; i < string.length(); i++) {
			if (letters[string.charAt(i)])
				return false;
			else
				letters[string.charAt(i)] = true;
		}
		return true;
	}

	// Using bit vector - max support is till UTF-64
	private static boolean solution1_2(String string) {
		return (string.length() < maxCharacters("ASCII")) && ((string.length() == 0) || solution1_2_helper(string));
	}

	private static boolean solution1_2_helper(String string) {
		int bitVector = 0;
		for (int i = 0; i < string.length(); i++) {
			if ((bitVector & (1 << string.charAt(i))) != 0)
				return false;
			bitVector |= (1 << string.charAt(i));
		}
		return true;
	}

	private static boolean solution1_3(String string) {
		return (string.length() < maxCharacters("UTF-8")) && ((string.length() == 0) || solution1_3_helper(string));
	}

	private static boolean solution1_3_helper(String string) {
		char[] ar = string.toCharArray();
		Arrays.sort(ar);
		char previous = string.charAt(0);

		for (int i = 1; i < string.length(); i++) {
			if (previous == string.charAt(i))
				return false;
		}
		return true;
	}

	private static void question2() {
		System.out.println("Q2. Check if the given strings are permutations of each other");
		checkArgument(solution2_1("Jai is going home", "Jai is going home"));
		checkArgument(solution2_1("Jai is giong home", "Jai is going home"));
		checkArgument(solution2_1("", ""));
		checkArgument(solution2_1("101", "110"));
		checkArgument(solution2_1("1>1", ">11"));
		checkArgument(solution2_1("101", "100") == false);
		checkArgument(solution2_1("Jai is going home", "Jai iis going home") == false);
	}

	private static boolean solution2_1(String string1, String string2) {
		// There are only 128 characters in ASCII and 256 in Unicode hence ask
		// the Character Set
		return (string1.length() == string2.length()) && solution2_1_helper(string1, string2, "ASCII");
	}

	private static boolean solution2_1_helper(String string1, String string2, String charset) {
		int length = maxCharacters(charset);

		int[] letters = new int[length];
		for (int i = 0; i < string1.length(); i++)
			letters[string1.charAt(i)]++;

		// checking after reducing each time - could have also checked at the
		// very end
		for (int i = 0; i < string2.length(); i++) {
			// If length is same and the strings are not same atleast one
			// character count will
			// be less than 0 to compensate for the character count greater than
			// 0
			if (--letters[string2.charAt(i)] < 0)
				return false;
		}
		return true;
	}

	private static void question3() {
		System.out.println("Q3. URLify the string i.e convert each space to %20");

		// Using new arrays
		checkArgument(solution3_1("Jai is going home").equals("Jai%20is%20going%20home"));
		checkArgument(solution3_1("").equals(""));
		checkArgument(solution3_1("101").equals("101"));

		// In place - with assumption that original array contains the required
		// extra space
		checkArgument(solution3_2("Jai   ".toCharArray(),"Jai ".length() ).equals("Jai%20"));
		checkArgument(solution3_2(" Jai  ".toCharArray()," Jai".length()).equals("%20Jai"));
		checkArgument(solution3_2("      ".toCharArray(),"  ".length()).equals("%20%20"));
		checkArgument(solution3_2("".toCharArray(),"".length()).equals(""));
		checkArgument(solution3_2("101".toCharArray(),"101".length()).equals("101"));
		checkArgument(solution3_2("10 1  ".toCharArray(),"10 1".length()).equals("10%201"));
	}

	// Using new array
	private static String solution3_1(String string) {
		char[] ar = string.toCharArray();
		int numOfSpaces = 0;
		for (char ch : ar) {
			if (ch == ' ')
				numOfSpaces++;
		}
		char[] newAr = new char[ar.length + numOfSpaces * 2];
		int offset = 0;
		for (int i = 0; i < ar.length; i++) {
			if (ar[i] != ' ')
				newAr[i + offset] = ar[i];
			else {
				newAr[i + offset] = '%';
				newAr[i + offset + 1] = '2';
				newAr[i + offset + 2] = '0';
				offset += 2;
			}
		}
		return new String(newAr);
	}

	// In place with spacious array and true length given

	private static String solution3_2(char[] charArray, int length) {
		int offset = 0;
		for(int i = length - 1; i>=0; i--) {
			if(charArray[i] != ' ') 
				charArray[charArray.length - offset++ - 1] = charArray[i];
			else {
				charArray[charArray.length - offset++ - 1] = '0';
				charArray[charArray.length - offset++ - 1] = '2';
				charArray[charArray.length - offset++ - 1] = '%';
			}
		}
		return new String(charArray);
	}

	private static int maxCharacters(String charset) {
		if (charset == "ASCII")
			return 128;
		if (charset == "UTF-8")
			return 256;
		if (charset == "UTF-16")
			return 65536;
		return 0;
	}
}

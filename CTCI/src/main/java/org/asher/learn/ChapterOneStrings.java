package org.asher.learn;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.jar.Attributes;

import static com.google.common.base.Preconditions.checkArgument;

// ASCII - 7 bits - 2^7 -> 0 - 128
// UTF-8 - 8 bits - 1 byte -> 0 - 256 with 1st 128 characters same as ASCII
// UTF-32 - 32 bits - 4 bytes - should fit int but doesn;t
public class ChapterOneStrings {
	public static void run() {
		System.out.println("\n****\nRunning Chapter 1 - Strings\n****");
		question1();
		question2();
		question3();
		question4();
		question5();
		question6();
		question7();
		question8();
		question9();
		extraQuestion();
	}

	private static void extraQuestion() {
		// Combination means that order doesn't matter
		// Order matters in Permutations
		System.out.println("Extra Question: Find all permutations of a String");
		solutionExtra();
	}

	private static void solutionExtra() {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		String string = "Jai";
		findPermutation("", string, list);
		checkArgument(list.size() == factorial(string.length()));

	}

	private static void findPermutation(String prefix, String str, List<String> list) {
		int n = str.length();
		if (n == 0) {
			list.add(prefix);
			return;
		}
		for (int i = 0; i < n; i++)
			findPermutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n), list);
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
		checkArgument(solution3_2("Jai   ".toCharArray(), "Jai ".length()).equals("Jai%20"));
		checkArgument(solution3_2(" Jai  ".toCharArray(), " Jai".length()).equals("%20Jai"));
		checkArgument(solution3_2("      ".toCharArray(), "  ".length()).equals("%20%20"));
		checkArgument(solution3_2("".toCharArray(), "".length()).equals(""));
		checkArgument(solution3_2("101".toCharArray(), "101".length()).equals("101"));
		checkArgument(solution3_2("10 1  ".toCharArray(), "10 1".length()).equals("10%201"));
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
		for (int i = length - 1; i >= 0; i--) {
			if (charArray[i] != ' ')
				charArray[charArray.length - offset++ - 1] = charArray[i];
			else {
				charArray[charArray.length - offset++ - 1] = '0';
				charArray[charArray.length - offset++ - 1] = '2';
				charArray[charArray.length - offset++ - 1] = '%';
			}
		}
		return new String(charArray);
	}

	private static void question4() {
		System.out.println("Q4. Check if the String can be converted to a palindrome - ignore whitespaces");
		// Using boolean array
		checkArgument(solution4_1("Palindro Pal\tindro", "UTF-8"));
		checkArgument(solution4_1("Palindro i\tPalindro", "UTF-16"));
		checkArgument(solution4_1("Palindro\t\t\tPalindro", "UTF-8"));
		checkArgument(solution4_1(" ", "UTF-8"));
		checkArgument(solution4_1("  ", "UTF-16"));
		checkArgument(solution4_1("", "ASCII"));
		checkArgument(!solution4_1("JA\tI", "UTF-16"));
		checkArgument(!solution4_1("JA\tA  \nI", "UTF-8"));

		// Using bit vector
		checkArgument(solution4_2("Palindro Pal\tindro"));
		checkArgument(solution4_2("Palindro i\tPalindro"));
		checkArgument(solution4_2("Palindro\t\t\tPalindro"));
		checkArgument(solution4_2(" "));
		checkArgument(solution4_2("  "));
		checkArgument(solution4_2(""));
		checkArgument(!solution4_2("JA\tI"));
		checkArgument(!solution4_2("JA\tA  \nI"));
	}

	// Using boolean array
	private static boolean solution4_1(String str, String charset) {
		String string = str.replaceAll("\\s+", "");
		boolean[] letters = new boolean[maxCharacters(charset)]; // default is
																	// false
		for (int i = 0; i < string.length(); i++)
			letters[string.charAt(i)] = !letters[string.charAt(i)];

		boolean sawOnce = false;
		for (boolean letter : letters) {
			if (sawOnce && letter)
				return false;
			else if (letter)
				sawOnce = true;
		}
		return true;
	}

	// Using bit vector which supports upto UTF-64
	private static boolean solution4_2(String str) {
		int bitVector = 0;
		String string = str.replaceAll("\\s+", "");
		for (int i = 0; i < string.length(); i++) {
			bitVector = toggle(bitVector, string.charAt(i));
		}

		// check if only one bit set
		// if only one bit is in a number set then the number is a power of 2
		// (not just a multiple)
		// hence number - 1 causes all bits < then set bit to be 1 and hence
		// (number) & (number - 1) = 0
		// eg:- 0100 - 1 = 0011 , 1011 - 1 = 1010
		if (bitVector == 0 || (bitVector & (bitVector - 1)) == 0)
			return true;
		return false;
	}

	private static int toggle(int bitVector, char position) {
		if ((bitVector & (1 << position)) == 0) {
			// bit is unset - set it
			bitVector |= (1 << position);
		} else {
			// bit is set - unset it
			bitVector &= ~(1 << position);
		}
		return bitVector;
	}

	private static void question5() {
		System.out.println(
				"Q5. Check if the 2 strings are atmostone edit apart, an edit is an insert, delete or replace");
		checkArgument(solution5_1("Jai", "Jai"));
		checkArgument(solution5_1("", "i"));
		checkArgument(solution5_1("ai", "i"));
		checkArgument(solution5_1("iS", "iSl"));
		checkArgument(!solution5_1("JiS", "iSl"));
		checkArgument(!solution5_1("JSp", "iSl"));
		checkArgument(!solution5_1("JSpo", "iSp"));
		checkArgument(solution5_1("JSpo", "JSto"));
		checkArgument(!solution5_1("JSpo", "Jpto"));
	}

	private static boolean solution5_1(String string1, String string2) {
		if (string1.length() > string2.length()) {
			return isOneAddAway(string1, string2);
		} else if (string1.length() < string2.length()) {
			return isOneAddAway(string2, string1);
		}
		// Equals
		return isOneReplaceAway(string1, string2);
	}

	private static boolean isOneAddAway(String large, String small) {
		if (large.length() - small.length() > 1) {
			return false;
		}
		boolean diffFound = false;
		int largeIndex = 0;
		for (int i = 0; i < small.length(); i++) {
			if (small.charAt(i) != large.charAt(largeIndex)) {
				if (diffFound) // one add already done.
					return false;
				diffFound = true;
				largeIndex++;
			}
			largeIndex++;
		}
		return true;

	}

	private static boolean isOneReplaceAway(String string1, String string2) {
		boolean diffFound = false;
		for (int i = 0; i < string1.length(); i++) {
			if (string1.charAt(i) != string2.charAt(i)) {
				if (diffFound) // one replace already done
					return false;
				diffFound = true;
			}
		}
		return true;
	}

	private static void question6() {
		System.out.println("Q6. Compress adjacent characters in a string");
		checkArgument(solution6_1("abca").equals("abca"));
		checkArgument(solution6_1("abba").equals("ab2a"));
		checkArgument(solution6_1("a").equals("a"));
		checkArgument(solution6_1("aaa").equals("a3"));
		checkArgument(solution6_1("abcc").equals("abc2"));
	}

	private static String solution6_1(String string) {
		if (string.length() < 2)
			return string;
		StringBuilder builder = new StringBuilder();
		char[] ar = string.toCharArray();
		int count = 1;
		char previous = ar[0];
		for (int i = 1; i < ar.length; i++) {
			if (ar[i] != previous) {
				if (count > 1) {
					builder.append(previous);
					builder.append(count);
				} else {
					builder.append(previous);
				}
				count = 1;
				previous = ar[i];
			} else {
				count++;
			}
		}

		// handling last character
		if (count > 1) {
			builder.append(previous);
			builder.append(count);
		} else {
			builder.append(previous);
		}
		return builder.toString();
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

	private static void question7() {
		System.out.println("Q7. Rotate Matrix - REDO the in place solution");
		char[][] ar1 = { { 'a', 'b', 'c' }, { 'd', 'e', 'f' }, { 'g', 'h', 'i' } };
		char[][] resultAr1 = { { 'c', 'f', 'i' }, { 'b', 'e', 'h' }, { 'a', 'd', 'g' } };
		checkArgument(toString(solution7_1(ar1)).equals(toString(resultAr1)));

		char[][] ar2 = { { 'a', 'b' }, { 'd', 'e' } };
		char[][] resultAr2 = { { 'b', 'e' }, { 'a', 'd' } };
		checkArgument(Arrays.deepEquals(solution7_1(ar2), resultAr2));

		char[][] ar3 = { { 'a', 'b', 'c' }, { 'd', 'e', 'f' }, { 'g', 'h', 'i' } };
		char[][] resultAr3 = { { 'c', 'f', 'i' }, { 'b', 'e', 'h' }, { 'a', 'd', 'g' } };
		checkArgument(toString(solution7_2(resultAr3, 3)).equals(toString(ar3)));

		char[][] ar4 = { { 'a', 'b' }, { 'd', 'e' } };
		char[][] resultAr4 = { { 'b', 'e' }, { 'a', 'd' } };
		checkArgument(Arrays.deepEquals(solution7_2(resultAr4, 2), ar4));
	}

	// Method requires a new matrix to be constructed - Clockwise
	private static char[][] solution7_1(char[][] ar) {
		char[][] newMAtrix = new char[ar.length][ar.length];
		for (int i = 0; i < ar.length; i++) {
			for (int j = 0; j < ar[i].length; j++) {
				newMAtrix[i][j] = ar[j][ar.length - i - 1];
			}
		}
		return newMAtrix;
	}

	// Method requires a new matrix to be constructed - Anti-clockwise
	private static char[][] solution7_2(char[][] matrix, int n) {
		for (int layer = 0; layer < n / 2; ++layer) {
			int first = layer;
			int last = n - 1 - layer;
			for (int i = first; i < last; ++i) {
				int offset = i - first;
				char top = matrix[first][i]; // save top

				// left -> top
				matrix[first][i] = matrix[last - offset][first];

				// bottom -> left
				matrix[last - offset][first] = matrix[last][last - offset];

				// right -> bottom
				matrix[last][last - offset] = matrix[i][last];

				// top -> right
				matrix[i][last] = top; // right <- saved top
			}
		}
		return matrix;
	}

	private static String toString(char[][] a) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				builder.append(a[i][j]);
				builder.append(' ');
			}
			builder.append('\n');
		}
		return builder.toString();
	}

	private static void question8() {
		System.out.println("Q8. Given an NXN matrix, if the element is 0 - nullify (0) the entire row and column");
		char[][] ar1 = { { 'a', 'b', 'c' }, { 'd', '0', 'f' }, { 'g', 'h', 'i' } };
		char[][] resultAr1 = { { 'a', '0', 'c' }, { '0', '0', '0' }, { 'g', '0', 'i' } };
		checkArgument(Arrays.deepEquals(solution8_1(ar1), resultAr1));
		char[][] ar2 = { { 'a', 'b', 'c' }, { 'd', 'e', 'f' }, { 'g', 'h', '0' } };
		char[][] resultAr2 = { { 'a', 'b', '0' }, { 'd', 'e', '0' }, { '0', '0', '0' } };
		checkArgument(Arrays.deepEquals(solution8_1(ar2), resultAr2));
		char[][] ar3 = { { 'a', 'b', 'c' }, { 'd', 'e', 'f' }, { 'g', 'h', 'i' } };
		char[][] resultAr3 = { { 'a', 'b', 'c' }, { 'd', 'e', 'f' }, { 'g', 'h', 'i' } };
		checkArgument(Arrays.deepEquals(solution8_1(ar3), resultAr3));
	}

	private static char[][] solution8_1(char[][] ar) {
		boolean firstRowHasZero = false;
		boolean firstColumnHasZero = false;

		for (int i = 0; i < ar.length; i++) {
			if (ar[i][0] == '0') {
				firstColumnHasZero = true;
				break;
			}
		}

		for (int i = 0; i < ar[0].length; i++) {
			if (ar[0][i] == '0') {
				firstRowHasZero = true;
				break;
			}
		}

		for (int i = 1; i < ar.length; i++) {
			for (int j = 1; j < ar[i].length; j++) {
				if (ar[i][j] == '0') {
					ar[0][j] = '0';
					ar[i][0] = '0';
				}
			}
		}

		for (int i = 1; i < ar.length; i++) {
			for (int j = 1; j < ar[i].length; j++) {
				if (ar[0][j] == '0' || ar[i][0] == '0')
					ar[i][j] = '0';
			}
		}

		if (firstColumnHasZero) {
			for (int i = 0; i < ar.length; i++) {
				ar[i][0] = '0';
			}
		}

		if (firstRowHasZero) {
			for (int j = 0; j < ar[0].length; j++) {
				ar[0][j] = '0';
			}
		}
		return ar;
	}

	private static void question9() {
		System.out.println("Q9. Determine is a string is left or right shifted");
		checkArgument(solution9_1("Jai", "aiJ"));
		checkArgument(!solution9_1("Jai", "aJ"));
		checkArgument(!solution9_1("J i", " Ji"));
		checkArgument(solution9_1("J i", "iJ "));
	}

	private static boolean solution9_1(String string1, String string2) {
		return (string1.length() == string2.length()) && (string1 + string1).contains(string2);
	}
	
	public static int factorial(int n) {
	    int x = 1;
	    int y = 1;
	    for (int i = 1; i <= n; i++) {
	        y = x * i;
	        x = y;
	    }
	    return y;
	}
}

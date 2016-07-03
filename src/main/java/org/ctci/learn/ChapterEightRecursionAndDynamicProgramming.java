package org.ctci.learn;

import static com.google.common.base.Preconditions.checkArgument;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ctci.utils.Point;
import org.ctci.utils.Utilities;

public class ChapterEightRecursionAndDynamicProgramming {

	// - Recursion - Bottom up, Top Down
	// - All recursions can be converted to iteration since recursion takes more
	// space (w/o tail call optimization)
	// - Dynamic Programming = Recursion + caching intermediate results
	public static void run() {
		System.out.println("\n****\nRunning Chapter 8 - Recursion And Dynamic Programming\n****");
		testBigInteger();
		question1();
		question2();
		question3();
		question4();
		question5();
		question6();
		question7();
		question8();
		question9();
		question10();
		question11();
		question12();
	}

	private static void question9() {
		System.out.println("Q9. Given 2 n paranthesis - n open and n close, WAP to find number of valid code blocks.");
		solution9();
	}

	private static void solution9() {
		List<String> paranthesisList = getValidParaenthesis(2);
		checkArgument(paranthesisList.size() == 2);
		for (String paranthesis : paranthesisList)
			checkArgument(validateParanthesis(paranthesis));
		checkArgument(!Utilities.containsDuplicate(paranthesisList));

		paranthesisList = getValidParaenthesis(3);
		checkArgument(paranthesisList.size() == 5);
		for (String paranthesis : paranthesisList)
			checkArgument(validateParanthesis(paranthesis));
		checkArgument(!Utilities.containsDuplicate(paranthesisList));

		paranthesisList = getValidParaenthesis(4);
		checkArgument(paranthesisList.size() == 14);
		for (String paranthesis : paranthesisList)
			checkArgument(validateParanthesis(paranthesis));
		checkArgument(!Utilities.containsDuplicate(paranthesisList));

		paranthesisList = getValidParaenthesis(5);
		checkArgument(paranthesisList.size() == 42);
		for (String paranthesis : paranthesisList)
			checkArgument(validateParanthesis(paranthesis));
		checkArgument(!Utilities.containsDuplicate(paranthesisList));
	}

	private static boolean validateParanthesis(String paranthesis) {
		int openBlocksCount = 0;
		for (int i = 0; i < paranthesis.length(); i++) {
			if (paranthesis.charAt(i) == '{')
				openBlocksCount++;
			else if (paranthesis.charAt(i) == '}')
				openBlocksCount--;
			else
				return false;
			if (openBlocksCount < 0) // close braces before open
				return false;

		}
		return openBlocksCount == 0;
	}

	private static List<String> getValidParaenthesis(int numOfPraranthesis) {
		// Similar to findPermutationWithDuplicates
		List<String> validParenthesis = new ArrayList<String>();
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		map.put('{', numOfPraranthesis);
		map.put('}', numOfPraranthesis);
		getValidParaenthesis("", map, validParenthesis);
		return validParenthesis;
	}

	private static void getValidParaenthesis(String prefix, HashMap<Character, Integer> map,
			List<String> validParenthesis) {

		if (allKeysHaveValue(map, 0)) {
			validParenthesis.add(prefix);
			return;
		}

		if (map.get('{') > 0) {
			map.put('{', map.get('{') - 1);
			getValidParaenthesis(prefix + '{', map, validParenthesis);
			map.put('{', map.get('{') + 1);
		}

		if (map.get('}') > 0 && map.get('{') < map.get('}')) {
			map.put('}', map.get('}') - 1);
			getValidParaenthesis(prefix + '}', map, validParenthesis);
			map.put('}', map.get('}') + 1);
		}
	}

	// TODO -
	private static void question10() {
		System.out.println(
				"Q10. WAP to simulate paintFill function, i.e change the colors till some other color (boundary) is encountered");
		solution10();
	}

	private static void solution10() {
		Character[][] board = { { 'R', 'R', 'R', 'G', 'R' }, { 'R', 'R', 'R', 'G', 'R' }, { 'R', 'R', 'R', 'G', 'R' },
				{ 'R', 'R', 'R', 'G', 'R' }, { 'R', 'R', 'G', 'R', 'R' }, { 'R', 'R', 'G', 'R', 'R' } };
		paintFill(board, 2, 2, 'B');
		Character[][] result = { { 'B', 'B', 'B', 'G', 'R' }, { 'B', 'B', 'B', 'G', 'R' }, { 'B', 'B', 'B', 'G', 'R' },
				{ 'B', 'B', 'B', 'G', 'R' }, { 'B', 'B', 'G', 'R', 'R' }, { 'B', 'B', 'G', 'R', 'R' }, };
		checkArgument(Utilities.isEqual(result, board));
	}

	private static void paintFill(Character[][] board, int i, int j, char newColor) {
		paintFillHelper(board, i, j, newColor, board[i][j]);
	}

	private static void paintFillHelper(Character[][] board, int i, int j, char newColor, Character currentColor) {
		if (i < 0 || j < 0 || j > board[0].length - 1 || i > board.length - 1) // Out
																				// of
																				// bounds
			return;
		if (board[i][j] != currentColor) // Stop recursion
			return;

		board[i][j] = newColor;
		paintFillHelper(board, i + 1, j, newColor, currentColor);
		paintFillHelper(board, i - 1, j, newColor, currentColor);
		paintFillHelper(board, i, j + 1, newColor, currentColor);
		paintFillHelper(board, i, j - 1, newColor, currentColor);
	}

	private static void question11() {
		System.out.println(
				"Q11. Given infinite number of 25, 10, 5 and 2 cents, find number of ways to represent n cents.");
		solution11();
	}

	// TODO - better test coverage and code shrinkage
	private static void solution11() {
		List<List<Integer>> listOfList = new ArrayList<List<Integer>>();
		Integer[] denomination = { 25, 10, 5, 2 };
		ArrayList<List<Integer>> result = new ArrayList<List<Integer>>();
		int numOfWays = createChange(10, denomination, 0, new ArrayList<Integer>(), result);
		checkArgument(numOfWays == 3);
		// for(List<Integer> list : result) {
		// System.out.println();
		// for(Integer integer : list)
		// System.out.print(integer + " ");
		// }

		result = new ArrayList<List<Integer>>();
		numOfWays = createChange(5, denomination, 0, new ArrayList<Integer>(), result);
		checkArgument(numOfWays == 1);
		// for(List<Integer> list : result) {
		// System.out.println();
		// for(Integer integer : list)
		// System.out.print(integer + " ");
		// }

		result = new ArrayList<List<Integer>>();
		numOfWays = createChange(15, denomination, 0, new ArrayList<Integer>(), result);
		checkArgument(numOfWays == 9);
		// for(List<Integer> list : result) {
		// System.out.println();
		// for(Integer integer : list)
		// System.out.print(integer + " ");
		// }
	}

	private static int createChange(int target, Integer[] denomination, int current, List<Integer> pathSofar,
			List<List<Integer>> finalPath) {
		if (pathSofar.size() > 0)
			pathSofar.add(current);
		else
			pathSofar.add(current);

		if (current == target) {
			finalPath.add(pathSofar);
			return 1;
		}
		int numOfWays = 0;
		for (Integer integer : denomination) {
			if (current + integer <= target)
				numOfWays += createChange(target, denomination, current + integer, new ArrayList<Integer>(pathSofar),
						finalPath);
		}
		return numOfWays;
	}

	private static void question12() {
		System.out.println(
				"Q12. Eight queens problem - place the queens on a chess board in such a way that they can't kill each other.");
		solution12();
	}

	private static void solution12() {
		Character[][] board = getValidEightQueenConfig();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] == 'Q')
					checkArgument(isValidPosition(i, j, board));
			}
		}
	}

	private static Character[][] getValidEightQueenConfig() {
		Character[][] board = new Character[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++)
				board[i][j] = ' ';
		}
		getValidEightQueenConfigHelper(board, 0);
		return board;
	}

	private static boolean getValidEightQueenConfigHelper(Character[][] board, int r) {
		if (r >= 8)
			return true;
		for (int i = 0; i < 8; i++) {
			board[r][i] = 'Q';
			if (isValidPosition(r, i, board) && getValidEightQueenConfigHelper(board, r + 1))
				return true;
			board[r][i] = ' ';
		}
		return false;
	}

	private static boolean isValidPosition(int row, int col, Character[][] board) {
		// Redundant check to see if there is no other Q on the same row
		// different column
		for (int i = 0; i < 8; i++) {
			if (i == col)
				continue;
			if (board[row][i] == 'Q')
				return false;
		}

		// Check to see if there is no Q on the same column, different row
		for (int i = 0; i < 8; i++) {
			if (i == row)
				continue;
			if (board[i][col] == 'Q')
				return false;
		}

		for (int r = 0; r < 8; r++) {
			if (r == row)
				continue;
			for (int c = 0; c < 8; c++) {
				if (c == col)
					continue;
				if (Math.abs(r - row) == Math.abs(c - col) && board[r][c] == 'Q')
					return false;
			}
		}
		return true;
	}

	private static void question5() {
		System.out.println("Q5: Multiply 2 numbers using only addition, shift and mod.");
		solution5();
	}

	// Normal for loop may take O(N) - we can do better O(log N) using Divide
	// and conquer
	private static void solution5() {
		long result = customMultiplication(5, 10);
		checkArgument(result == 50);
		result = customMultiplication(-1, 10);
		checkArgument(result == -10);
		result = customMultiplication(-2, -10);
		checkArgument(result == 20);
	}

	private static long customMultiplication(int num1, int num2) {
		boolean positiveResult = true;
		if (num1 < 0)
			positiveResult = !positiveResult;
		if (num2 < 0)
			positiveResult = !positiveResult;
		num1 = Math.abs(num1);
		num2 = Math.abs(num2);
		long[] array = new long[num1 < num2 ? num2 : num1];
		for (int i = 0; i < array.length; i++) {
			array[i] = 0;
		}
		long result;
		if (num1 < num2)
			result = num2 == 0 ? 0 : customMultiplicationHelper(num1, num2, array);
		else
			result = num1 == 0 ? 0 : customMultiplicationHelper(num1, num2, array);
		return positiveResult ? result : result * -1;
	}

	private static long customMultiplicationHelper(int smaller, int larger, long[] cachedValue) {
		if (smaller == 0)
			return 0;
		if (smaller == 1)
			return larger;
		long result = (cachedValue[smaller >> 1] != 0) ? cachedValue[smaller >> 1]
				: customMultiplicationHelper(smaller >> 1, larger, cachedValue);
		cachedValue[smaller >> 1] = result;
		if (smaller % 2 == 0)
			result += result;
		else {
			result += (cachedValue[smaller - (smaller >> 1)] != 0) ? cachedValue[smaller - (smaller >> 1)]
					: customMultiplicationHelper(smaller - (smaller >> 1), larger, cachedValue);
			cachedValue[smaller - (smaller >> 1)] = result;
		}
		cachedValue[smaller] = result;
		return result;
	}

	private static void question8() {
		System.out.println("Q8: Find all unique permutations of a String containing duplicate characters.");
		solution8();

	}

	private static void solution8() {
		String string = "Jai";
		List<String> list = findPermutationWithDuplicates(string);
		checkArgument(list.size() == Utilities.factorial(string.length()));
		checkArgument(!Utilities.containsDuplicate(list));

		string = "Jaai";
		list = findPermutationWithDuplicates(string);
		// 2 is the number of duplicate elements in the string
		checkArgument(list.size() == Utilities.factorial(string.length()) / 2);
		checkArgument(!Utilities.containsDuplicate(list));

		string = "Jaaiss";
		list = findPermutationWithDuplicates(string);
		// 4 (2 a and 2 s) is the number of duplicate elements in the string
		checkArgument(list.size() == Utilities.factorial(string.length()) / 4);
		checkArgument(!Utilities.containsDuplicate(list));
	}

	private static List<String> findPermutationWithDuplicates(String string) {
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		for (int i = 0; i < string.length(); i++) {
			Character c = string.charAt(i);
			map.put(c, map.getOrDefault(c, 0) + 1);
		}
		List<String> list = new ArrayList<String>();
		findPermutationWithDuplicates("", map, list);
		return list;
	}

	// result shouldn't contain duplicates
	private static void findPermutationWithDuplicates(String prefix, HashMap<Character, Integer> map,
			List<String> result) {

		if (allKeysHaveValue(map, 0)) {
			result.add(prefix);
			return;
		}

		for (Character character : map.keySet()) {
			if (map.get(character) <= 0)
				continue;
			map.put(character, map.get(character) - 1);
			findPermutationWithDuplicates(prefix + character, map, result);
			map.put(character, map.get(character) + 1);
			// map is shared between the recursions so making sure that it
			// remains unchanged
		}
	}

	private static <K, V> boolean allKeysHaveValue(HashMap<K, V> map, V expectedValue) {
		for (K key : map.keySet()) {
			if (!map.get(key).equals(expectedValue))
				return false;
		}
		return true;
	}

	private static void question7() {
		// Combination means that order doesn't matter
		// Order matters in Permutations
		System.out.println("Q7: Find all unique permutations of a String containing unique characters.");
		solution7();
	}

	private static void solution7() {
		List<String> list = new ArrayList<String>();
		String string = "Jai";
		findPermutation("", string, list);
		checkArgument(list.size() == Utilities.factorial(string.length()));
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

	private static void question6() {
		System.out.println("Q6. Tower of Hanoi");
		checkArgument(towerOfHanoi(3, 'A', 'B', 'C').equals("Move Element 1 from A to C\n"
				+ "Move Element 2 from A to B\n" + "Move Element 1 from C to B\n" + "Move Element 3 from A to C\n"
				+ "Move Element 1 from B to A\n" + "Move Element 2 from B to C\n" + "Move Element 1 from A to C\n"));
	}

	/*
	 * Tower of Hanoi n == 1: move element1 from src to target n == 2: move
	 * element1 from src to temp using target as temp move element1 from src to
	 * target using temp as temp move element2 from temp to target using src as
	 * target n == 3: move element1, element2 from src to temp using target as
	 * temp move element3 from src to target using temp as temp move element1,
	 * element2 from temp to target using src as temp
	 */
	private static String towerOfHanoi(int n, char src, char temp, char target) {
		if (n == 1)
			return "Move Element " + 1 + " from " + src + " to " + target + "\n";
		String string = towerOfHanoi(n - 1, src, target, temp);
		string += "Move Element " + n + " from " + src + " to " + target + "\n";
		string += towerOfHanoi(n - 1, temp, src, target);
		return string;
	}

	private static void question4() {
		System.out.println("Q4. Given a set of distinct values find all subsets.");
		// This is different from ChapterOneStrings.findPermutation because in
		// this case we need subsets of different length
		// but don't need their permutations. Eg {1, 2} = {{}, {1} , {2}, {1,
		// 2}} and not {2, 1}
		// Hence we will use the approach of adding each element to the list one
		// by one,
		solution4();
	}

	private static void solution4() {
		List<Float> set = new ArrayList<Float>();
		set.add(1.0f);
		set.add(3.0f);
		List<List<Float>> listOfSet = findSubSet(set);
		checkArgument(listOfSet.size() == 4);

		set.add(4.0f);
		listOfSet = findSubSet(set);
		checkArgument(listOfSet.size() == 8);
	}

	private static <T> List<List<T>> findSubSet(List<T> set) {
		List<List<T>> listOfList = new ArrayList<List<T>>();
		listOfList.add(new ArrayList<T>());
		findSubSetHelper(set, listOfList);
		return listOfList;
	}

	private static <T> void findSubSetHelper(List<T> set, List<List<T>> listOfList) {
		for (T element : set) {
			List<List<T>> tempListOfList = new ArrayList<List<T>>();
			for (List<T> subsets : listOfList) {
				List<T> list = new ArrayList<T>(subsets);
				list.add(element);
				tempListOfList.add(list);
			}
			listOfList.addAll(tempListOfList);
		}
	}

	private static void question3() {
		System.out.println("Q3. In an sorted array with duplicates, Magic Index is an index where a[i] == i");
		solution3();
	}

	// TODO - use divide and conquer approach like in the books
	// Add more test cases
	private static void solution3() {
		List<Integer> magicIndices = new ArrayList<Integer>();
		List<Integer> array = new ArrayList<Integer>();
		array.add(0);
		array.add(3);
		array.add(3);
		array.add(3);
		array.add(4);
		array.add(7);
		array.add(8);
		findMagicIndices(array, magicIndices);
		checkArgument(magicIndices.size() == 3);
		checkArgument(magicIndices.get(0) == 0);
		checkArgument(magicIndices.get(1) == 3);
		checkArgument(magicIndices.get(2) == 4);
	}

	private static void findMagicIndices(List<Integer> array, List<Integer> magicIndices) {
		for (int i = 0; i < array.size(); i++) {
			if (array.get(i) == i)
				magicIndices.add(i);
			else if (array.get(i) > i)
				i = array.get(i) - 1; // to account for i++ in the end
		}
	}

	private static void question2() {
		System.out.println(
				"Q2. Given a robot which can travel one step right or down and a board with a set of obstacles."
						+ "\n    Give a path for the robot from top left to bottom right corner, if a path exists.");
		solution2();

	}

	private static void solution2() {
		List<Point> path = new ArrayList<Point>();
		List<Point> obstacles = new ArrayList<Point>();
		path = findPath(new Point(0, 0), obstacles, new Point(4, 3));
		checkArgument(correctPath(new Point(0, 0), path, obstacles, new Point(4, 3)));

		obstacles.add(new Point(0, 2));
		obstacles.add(new Point(1, 2));
		obstacles.add(new Point(2, 2));
		obstacles.add(new Point(3, 2));
		path = findPath(new Point(0, 0), obstacles, new Point(4, 3));
		checkArgument(correctPath(new Point(0, 0), path, obstacles, new Point(4, 3)));

		obstacles.add(new Point(4, 2));
		path = findPath(new Point(0, 0), obstacles, new Point(4, 3));
		checkArgument(!correctPath(new Point(0, 0), path, obstacles, new Point(4, 3)));

	}

	private static boolean correctPath(Point start, List<Point> path, List<Point> obstacles, Point target) {
		if (path == null || path.size() < 1 || !path.get(0).equals(target))
			return false;

		Point prevPath = start;
		for (int i = path.size() - 1; i >= 0; i--) {
			int diffx = path.get(i).x - prevPath.x;
			int diffy = path.get(i).y - prevPath.y;
			int total = diffx + diffy;
			if (diffx > 1 || diffx < 0 || diffy > 1 || diffy < 0 || total != 1)
				return false;
			prevPath = path.get(i);
		}
		return true;
	}

	private static List<Point> findPath(Point point, List<Point> obstacles, Point target) {
		if (obstacles.contains(point) || point.x > target.x || point.y > target.y) {
			return null;
		}
		List<Point> path = new ArrayList<Point>();
		if (point.equals(target)) {
			return path;
		}
		List<Point> pathSoFar = findPath(point.add(1, 0), obstacles, target);
		if (pathSoFar != null) {
			path.addAll(pathSoFar);
			path.add(point.add(1, 0));
			return path;
		}

		pathSoFar = findPath(point.add(0, 1), obstacles, target);
		if (pathSoFar != null) {
			path.addAll(pathSoFar);
			path.add(point.add(0, 1));
			return path;
		}
		return null;
	}

	// Big Integer like String, Integer, Float is Immutable i.e value can't
	// change
	// Normal classes are mutable. Immutablt is more thread safe
	private static void testBigInteger() {
		boolean exceptionOccured = true;
		BigInteger bigInteger = null;
		try {
			bigInteger = new BigInteger("polo");
			exceptionOccured = false;
		} catch (Exception ex) {
			checkArgument(exceptionOccured);
		}
		BigInteger originalValue = new BigInteger("123456789123456789123456789123456789");
		bigInteger = originalValue;
		bigInteger.add(new BigInteger("1"));
		checkArgument(bigInteger.equals(originalValue));
		bigInteger = bigInteger.add(new BigInteger("1"));
		checkArgument(!bigInteger.equals(originalValue));
		checkArgument(bigInteger.equals(new BigInteger("123456789123456789123456789123456790")));
	}

	private static void question1() {
		System.out.println(
				"Q1. Find all possible ways to reach the top of the stairs assuming that the kid takes either 1, 2 or 3 stairs at a time.");
		checkArgument(possibleWaysToReachTopOfStairs(3).equals(new BigInteger("4")));
		checkArgument(possibleWaysToReachTopOfStairs(2).equals(new BigInteger("2")));
		checkArgument(possibleWaysToReachTopOfStairs(1).equals(new BigInteger("1")));
		// Just 100 stairs makes it out of Long.MAX_VALUE, hence we use Big
		// Integer
		checkArgument(possibleWaysToReachTopOfStairs(100).compareTo(new BigInteger(Long.toString(Long.MAX_VALUE))) > 0);
	}

	// Return type was BigInteger since worst case number of paths is
	// approximately
	// (3 ^ maxNumberOfFloors) which is exponentially large
	private static BigInteger possibleWaysToReachTopOfStairs(int maxNumberOfFloors) {
		BigInteger[] cachedResults = new BigInteger[maxNumberOfFloors + 1];
		for (int i = 0; i < cachedResults.length; i++)
			cachedResults[i] = new BigInteger("-1");
		return possibleWaysToReachTopOfStairs(maxNumberOfFloors, cachedResults);
	}

	private static BigInteger possibleWaysToReachTopOfStairs(int remainingFloors, BigInteger[] cachedResults) {
		if (remainingFloors < 0)
			return new BigInteger("0");
		else if (remainingFloors == 0)
			return new BigInteger("1");

		if (!cachedResults[remainingFloors].equals(new BigInteger("-1"))) {
			return cachedResults[remainingFloors];
		}
		BigInteger result = new BigInteger("0");
		if (remainingFloors >= 3) {
			result = result.add(possibleWaysToReachTopOfStairs(remainingFloors - 3, cachedResults));
		}
		if (remainingFloors >= 2) {
			result = result.add(possibleWaysToReachTopOfStairs(remainingFloors - 2, cachedResults));
		}
		result = result.add(possibleWaysToReachTopOfStairs(remainingFloors - 1, cachedResults));
		cachedResults[remainingFloors] = result;
		return result;
	}
}

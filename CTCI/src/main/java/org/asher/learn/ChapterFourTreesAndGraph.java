package org.asher.learn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import javax.sound.midi.MidiChannel;

import static com.google.common.base.Preconditions.checkArgument;

import org.apache.log4j.jmx.HierarchyDynamicMBean;
import org.asher.utils.BinaryTreeNode;
import org.w3c.dom.Node;

public class ChapterFourTreesAndGraph<T> {

	public static void run() {
		System.out.println("\n****\nRunning Chapter 4 - Trees and Map\n****");
		question1();
		question2();
		question3();
		question4();
		question5();
		question6();
		question7();
		question8();
		extraQuestion1();
	}

	private static void question1() {
		System.out.println(
				"Q1. Give a directed graph, design an algorithm to find out whether there is a route between two nodes.");
		// Just check if path exists
		// - loops are natural to BFS
		// - recursion is natural to DFS
		solution1_1();
		// Getting actual path using recursion and DFS
		solution1_2();
		// TODO:- Getting actual path using loops and BFS
		// In the remainingNodes and visitedNodes also keep track the parent
		// node
		// In the end back trace from dst to src using the visitedNodes
	}

	private static void solution1_2() {
		BinaryTreeNode<String> head = new BinaryTreeNode<String>("1");
		head.left = new BinaryTreeNode<String>("12");
		head.right = new BinaryTreeNode<String>("13");
		head.left.left = new BinaryTreeNode<String>("124");
		head.left.right = new BinaryTreeNode<String>("125");
		head.right.left = new BinaryTreeNode<String>("126");
		head.left.right.left = new BinaryTreeNode<String>("1257");
		BinaryTreeNode<String> tail = head.left.right.left;
		solution1_2_helper(head, tail, true);
		solution1_2_helper(head.left, tail, true);
		solution1_2_helper(head.left.left, head, false);
		solution1_2_helper(head.left.left, tail, false);
		solution1_2_helper(tail, head, false);

	}

	private static <T> void solution1_2_helper(BinaryTreeNode<T> src, BinaryTreeNode<T> dst, boolean expectedResult) {
		LinkedHashSet<BinaryTreeNode<T>> path = new LinkedHashSet<BinaryTreeNode<T>>();
		solution1_DFS_withPath(src, dst, null, path);
		if (expectedResult == false)
			checkArgument(path.size() == 0);
		else
			checkArgument(validatePath(src, dst, path));
	}

	private static <T> boolean solution1_DFS_withPath(BinaryTreeNode<T> src, BinaryTreeNode<T> dst,
			Set<BinaryTreeNode<T>> visited, Set<BinaryTreeNode<T>> path) {
		checkArgument(dst != null);

		if (visited == null)
			visited = new LinkedHashSet<BinaryTreeNode<T>>();

		if (src == null || !visited.add(src))
			return false;

		if (src == dst || solution1_DFS_withPath(src.left, dst, visited, path)
				|| solution1_DFS_withPath(src.right, dst, visited, path)) {
			path.add(src);
			return true;
		}
		return false;
	}

	private static void solution1_1() {
		BinaryTreeNode<String> head = new BinaryTreeNode<String>("1");
		head.left = new BinaryTreeNode<String>("12");
		head.right = new BinaryTreeNode<String>("13");
		head.left.left = new BinaryTreeNode<String>("124");
		head.left.right = new BinaryTreeNode<String>("125");
		head.right.left = new BinaryTreeNode<String>("126");
		head.left.right.left = new BinaryTreeNode<String>("1257");
		BinaryTreeNode<String> tail = head.left.right.left;
		checkArgument(solution1_1_BFS(head, tail));
		checkArgument(solution1_1_BFS(head.left, tail));
		checkArgument(!solution1_1_BFS(head.left.left, head));
		checkArgument(!solution1_1_BFS(head.left.left, tail));
		checkArgument(!solution1_1_BFS(tail, head));

		checkArgument(solution1_1_DFS(head, tail));
		checkArgument(solution1_1_DFS(head.left, tail));
		checkArgument(!solution1_1_DFS(head.left.left, head));
		checkArgument(!solution1_1_DFS(head.left.left, tail));
		checkArgument(!solution1_1_DFS(tail, head));
	}

	private static boolean solution1_1_DFS(BinaryTreeNode<String> src, BinaryTreeNode<String> dst) {
		return solution1_1_DFS(src, dst, null);
	}

	// In DFS the visited nodes list is **NOT** the path
	private static <T> boolean solution1_1_DFS(BinaryTreeNode<T> src, BinaryTreeNode<T> dst,
			Set<BinaryTreeNode<T>> visited) {
		checkArgument(dst != null);
		if (visited == null)
			visited = new HashSet<BinaryTreeNode<T>>();
		if (src == null || !visited.add(src)) // add returns false if element
												// already present
			return false;
		return src == dst || solution1_1_DFS(src.left, dst, visited) || solution1_1_DFS(src.right, dst, visited);
	}

	// In DFS the visited nodes list is **NOT** the path
	private static <T> boolean solution1_1_BFS(BinaryTreeNode<T> src, BinaryTreeNode<T> dst) {
		checkArgument(src != null);
		LinkedList<BinaryTreeNode<T>> visitedNodes = new LinkedList<BinaryTreeNode<T>>();
		LinkedList<BinaryTreeNode<T>> remainingNodes = new LinkedList<BinaryTreeNode<T>>();
		remainingNodes.push(src);
		while (remainingNodes.size() != 0) {
			BinaryTreeNode<T> node = remainingNodes.pop();
			if (visitedNodes.contains(node))
				continue;
			visitedNodes.add(node);
			if (node == dst)
				return true;
			if (node.left != null)
				remainingNodes.push(node.left);
			if (node.right != null)
				remainingNodes.push(node.right);
		}
		return false;
	}

	private static <T> boolean validatePath(BinaryTreeNode<T> start, BinaryTreeNode<T> end,
			LinkedHashSet<BinaryTreeNode<T>> path) {
		checkArgument(start != null && end != null);
		Object[] nodes = path.toArray();
		if (path == null || path.size() == 0 || start != nodes[nodes.length - 1])
			return false;

		for (int i = nodes.length - 2; i >= 0; i--) {
			if (nodes[i] == start.left)
				start = start.left;
			else if (nodes[i] == start.right)
				start = start.right;
			else
				return false;
		}
		if (start != end)
			return false;

		return true;
	}

	private static void question2() {
		System.out.println("Q2. Converted a sorted array into a Binary search tree of minimum height.");
		// Using Recursion
		solution2();

	}

	private static void solution2() {
		Integer[] array = { 2, 5, 9, 15, 1, 3, 0 };
		BinaryTreeNode<Integer> head = createBinaryTree(array, 0, array.length - 1);
		checkArgument(head.data == 15);
		checkArgument(head.left.data == 5);
		checkArgument(head.left.left.data == 2);
		checkArgument(head.left.right.data == 9);
		checkArgument(head.right.data == 3);
		checkArgument(head.right.left.data == 1);
		checkArgument(head.right.right.data == 0);

		Integer[] array2 = { 23, 22, -10, -101 };
		head = createBinaryTree(array2, 0, array2.length - 1);
		checkArgument(head.data == -10);
		checkArgument(head.left.data == 22);
		checkArgument(head.left.left.data == 23);
		checkArgument(head.right.data == -101);
	}

	private static <T> BinaryTreeNode<T> createBinaryTree(T[] array, int start, int finish) {
		if (start > finish)
			return null;
		BinaryTreeNode<T> node = new BinaryTreeNode<T>();
		// The + 1 is to make the uneven node goes to the left and not right
		// side
		// THIS DOESN't MAKE THE TREE BALANCED THOUGH
		int mid = (start + finish + 1) / 2;
		node.data = array[mid];
		node.right = createBinaryTree(array, mid + 1, finish);
		node.left = createBinaryTree(array, start, mid - 1);
		return node;
	}

	private static void question3() {
		System.out.println(
				"Q3. Given a binary tree of depth D, create D Linked list each containing the elements at that depth.");
		solution3();
	}

	private static void solution3() {
		Integer[] array = { 2, 5, 9, 15, 1, 3, 0, -10, -100, -101 };
		BinaryTreeNode<Integer> head = createBinaryTree(array, 0, array.length - 1);
		ArrayList<LinkedList<Integer>> arrayList = new ArrayList<LinkedList<Integer>>(array.length);
		createLinkedListForEachLevel(head, arrayList, 0);
		// Checking size
		checkArgument(arrayList.get(0).size() == 1);
		checkArgument(arrayList.get(1).size() == 2);
		checkArgument(arrayList.get(2).size() == 4);
		checkArgument(arrayList.get(3).size() == 3);

		// Checking values
		checkArgument(arrayList.get(0).get(0) == 3);

		checkArgument(arrayList.get(1).get(0) == 9);
		checkArgument(arrayList.get(1).get(1) == -100);

		checkArgument(arrayList.get(2).get(0) == 5);
		checkArgument(arrayList.get(2).get(1) == 1);
		checkArgument(arrayList.get(2).get(2) == -10);
		checkArgument(arrayList.get(2).get(3) == -101);

		checkArgument(arrayList.get(3).get(0) == 2);
		checkArgument(arrayList.get(3).get(1) == 15);
		checkArgument(arrayList.get(3).get(2) == 0);
	}

	private static void createLinkedListForEachLevel(BinaryTreeNode<Integer> head,
			ArrayList<LinkedList<Integer>> arrayList, int depth) {
		while (arrayList.size() <= depth)
			arrayList.add(new LinkedList<Integer>());
		if (head == null)
			return;
		arrayList.get(depth).add(head.data);
		createLinkedListForEachLevel(head.left, arrayList, depth + 1);
		createLinkedListForEachLevel(head.right, arrayList, depth + 1);
	}

	private static void question4() {
		System.out.println("Q4. Check if a binary tree is balanced.");
		// Height of tree is inverse of depth i.e head has the greatest Height
		// and depth of 1

		// Balanced tree is a tree in which the left and right nodes differ by
		// no more than one node
		solution4_1();

		// Balance tree in which all nodes are added to the left node first
		// (like in a heap)
		solution4_2();
	}

	private static void solution4_1() {
		BinaryTreeNode<String> head = new BinaryTreeNode<String>("1");
		head.left = new BinaryTreeNode<String>("12");
		head.right = new BinaryTreeNode<String>("13");
		head.left.left = new BinaryTreeNode<String>("124");
		head.left.right = new BinaryTreeNode<String>("125");
		head.right.left = new BinaryTreeNode<String>("126");
		head.right.left.right = new BinaryTreeNode<String>("1268");
		head.left.right.left = new BinaryTreeNode<String>("1257");
		checkArgument(checkAndGetHeightOfBalancedBinaryTree(head.left.right.left) == 1);
		checkArgument(checkAndGetHeightOfBalancedBinaryTree(head.left) == 3);
		checkArgument(checkAndGetHeightOfBalancedBinaryTree(head.right) == -1);
	}

	// Since all heights are positive -1 indicates that the tree is NOT balanced
	private static <T> Integer checkAndGetHeightOfBalancedBinaryTree(BinaryTreeNode<T> node) {
		if (node == null)
			return 0;
		int leftHeight = checkAndGetHeightOfBalancedBinaryTree(node.left);
		int rightHeight = checkAndGetHeightOfBalancedBinaryTree(node.right);
		if (leftHeight == -1 || rightHeight == -1 || Math.abs(leftHeight - rightHeight) > 1)
			return -1;
		return Math.max(leftHeight, rightHeight) + 1;
	}

	private static void solution4_2() {
		BinaryTreeNode<String> head = new BinaryTreeNode<String>("H");
		head.left = new BinaryTreeNode<String>("HL");
		head.right = new BinaryTreeNode<String>("HR");
		// checkArgument(checkIfBalancedHeap(head, 1));

		// Balanced so far
		head.left.left = new BinaryTreeNode<String>("HLL");
		initializeGlobalVariables();
		// checkArgument(checkIfBalancedHeap(head, 1));

		// Creating Imbalance
		head.right.left = new BinaryTreeNode<String>("HRL");
		initializeGlobalVariables();
		checkArgument(!checkIfBalancedHeap(head, 1));

		// Balancing again
		head.left.right = new BinaryTreeNode<String>("HLR");
		initializeGlobalVariables();
		checkArgument(checkIfBalancedHeap(head, 1));

		// Creating Imbalance
		head.right.left.left = new BinaryTreeNode<String>("HRLL");
		initializeGlobalVariables();
		checkArgument(!checkIfBalancedHeap(head, 1));

		// Not yet balanced
		head.right.right = new BinaryTreeNode<String>("HRR");
		initializeGlobalVariables();
		checkArgument(!checkIfBalancedHeap(head, 1));

		// Not yet balanced
		head.left.left.left = new BinaryTreeNode<String>("HLLL");
		initializeGlobalVariables();
		checkArgument(!checkIfBalancedHeap(head, 1));

		// Not yet balanced
		head.left.left.right = new BinaryTreeNode<String>("HLLR");
		initializeGlobalVariables();
		checkArgument(!checkIfBalancedHeap(head, 1));

		// Not yet balanced
		head.left.right.right = new BinaryTreeNode<String>("HLRR");
		initializeGlobalVariables();
		checkArgument(!checkIfBalancedHeap(head, 1));

		// Not yet balanced
		head.left.right.left = new BinaryTreeNode<String>("HLRL");
		initializeGlobalVariables();
		checkArgument(checkIfBalancedHeap(head, 1));
	}

	private static int maxAllowedDepth = 0;
	private static boolean nodeWithDepthLessThanMaxAllowedDepthFound = false;

	private static void initializeGlobalVariables() {
		maxAllowedDepth = 0;
		nodeWithDepthLessThanMaxAllowedDepthFound = false;
	}

	// TODO : Write down the logic
	public static <T> boolean checkIfBalancedHeap(BinaryTreeNode<T> node, int depth) {
		checkArgument(depth > 0);
		if (node == null) {
			if (maxAllowedDepth == 0) {
				maxAllowedDepth = depth;
				return true;
			}

			if (!nodeWithDepthLessThanMaxAllowedDepthFound && depth + 1 == maxAllowedDepth) {
				nodeWithDepthLessThanMaxAllowedDepthFound = true;
				return true;
			}

			return (!nodeWithDepthLessThanMaxAllowedDepthFound && depth == maxAllowedDepth)
					|| (nodeWithDepthLessThanMaxAllowedDepthFound && depth + 1 == maxAllowedDepth);
		}
		return checkIfBalancedHeap(node.left, depth + 1) && checkIfBalancedHeap(node.right, depth + 1);
	}

	private static void question5() {
		System.out.println("Q5. Validate a binary search tree.");
		solution_5();
	}

	private static void solution_5() {
		BinaryTreeNode<Integer> head = new BinaryTreeNode<Integer>(2);
		System.err.println(isBinarySearchTree(head));

		head.left = new BinaryTreeNode<Integer>(2);
		System.err.println(isBinarySearchTree(head));

		head.left = new BinaryTreeNode<Integer>(0);
		System.err.println(isBinarySearchTree(head));

		head.right = new BinaryTreeNode<Integer>(0);
		System.err.println(isBinarySearchTree(head));

		head.right = new BinaryTreeNode<Integer>(2);
		System.err.println(isBinarySearchTree(head));

		head.right.left = new BinaryTreeNode<Integer>(1);
		System.err.println(isBinarySearchTree(head));

		head.right = new BinaryTreeNode<Integer>(3);
		System.err.println(isBinarySearchTree(head));

		head.right.left = new BinaryTreeNode<Integer>(2);
		System.err.println(isBinarySearchTree(head));
	}

	private static <T extends Comparable<T>> boolean isBinarySearchTree(BinaryTreeNode<T> head) {
		return isBinarySearchTreeHelper(head, null, null);
	}

	// TODO - change this to generic type using compareTo
	private static <T extends Comparable<T>> boolean isBinarySearchTreeHelper(BinaryTreeNode<T> node, T maxValue,
			T minValue) {
		if (node == null)
			return true;

		// i.e ! (node <= maxValue && node > minValue)
		// This representation is required since we need to check maxValue or
		// minValue is null
		if ((maxValue == null && node.data.compareTo(maxValue) >= 0)
				|| (minValue == null && node.data.compareTo(minValue) < 0))
			return false;

		return isBinarySearchTreeHelper(node.left, node.data, minValue)
				&& isBinarySearchTreeHelper(node.right, maxValue, node.data);
	}

	private static void question6() {
		System.out.println("Q6. Given a BST find the next node in order of succession.");

	}

	private static void question7() {
		System.out.println(
				"Q7. Given a list of jobs and their dependencies, create a build order or return an error if no order can be created");
		System.out.println("\tprojects: a, b, c, d, e, f");
		System.out.println("\t(a, d), (f, b), (b, d), (f,a), (d, c)");
		System.out.println("\toutput: f, e, a. b, d, c");
	}

	private static void question8() {
		System.out.println("Q8. Given two nodes, find the least common ancestor in a BT.");
	}
	

	private static void extraQuestion1() {
		System.out.println("Extra: Convert the binary search tree into an array.");
		extraSolution1();
	}

	private static void extraSolution1() {
		ArrayList list = new ArrayList<String>();
		BinaryTreeNode<String> head = new BinaryTreeNode<String>("head");
		convertBSTToArrayList(head, list);
		System.out.println(list);
	}

	// Using In order traversal
	private static <T>void convertBSTToArrayList(BinaryTreeNode<T> node, ArrayList<T> list) {
		if (node == null)
			return;

		convertBSTToArrayList(node.left, list);
		list.add(node.data);
		convertBSTToArrayList(node.right, list);
	}

}

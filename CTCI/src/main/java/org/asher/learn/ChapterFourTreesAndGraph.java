package org.asher.learn;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import org.asher.utils.BinarySearchTree;
import org.asher.utils.BinaryTreeNode;
import org.asher.utils.Graph;
import org.asher.utils.GraphNode;
import org.asher.utils.Stack;
import org.asher.utils.Graph.GraphState;

public class ChapterFourTreesAndGraph<T> {

	public static class CustomBinaryTreeNode<T> {
		public CustomBinaryTreeNode(T data, CustomBinaryTreeNode<T> parent) {
			this.data = data;
			this.parent = parent;
		}

		public CustomBinaryTreeNode<T> parent = null;
		public CustomBinaryTreeNode<T> left = null;
		public CustomBinaryTreeNode<T> right = null;
		private T data = null;
	}

	// TODO - Split the class into 2 smaller ones
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
		question9();
		question10();
		question11();
		question12();
		extraQuestion1();
	}

	private static void question9() {
		System.out.println("Q9. Given a BST, print all possible arrays that could have led to this tree.");
		// We will get the same tree if we get all nodes at level X before level
		// X + 1 and after level X - 1
		BinaryTreeNode<Float> head = new BinaryTreeNode<Float>(4f);
		head.left = new BinaryTreeNode<Float>(2f);
		head.right = new BinaryTreeNode<Float>(6f);
		head.left.left = new BinaryTreeNode<Float>(1f);
		head.left.right = new BinaryTreeNode<Float>(3f);
		head.right.left = new BinaryTreeNode<Float>(5f);
		head.right.right = new BinaryTreeNode<Float>(7f);
		List<List<Float>> listOfList = getAllPossibleList(head);
		
		boolean hasDuplicate = false;
		for(int i = 0; i<listOfList.size();i++) {
			for(int j = 0; j<listOfList.size();j++) {
				if(i==j)
					continue;
				if(deepEquals(listOfList.get(i), listOfList.get(j))) 
					hasDuplicate = true;
			}
		}
		checkArgument(! hasDuplicate);
		checkArgument(listOfList.size() == 48);
		
		BinarySearchTree<Float> tree = new BinarySearchTree<Float>();
		tree.insertAll(listOfList.get(0));
		
		boolean incorrectTree = false;
		for(int i = 1; i<listOfList.size();i++) {
			BinarySearchTree<Float> newTree = new BinarySearchTree<Float>();
			newTree.insertAll(listOfList.get(i));
			if(! tree.equals(newTree)) {
				incorrectTree = true;
			}
		}
		checkArgument(! incorrectTree);
	}

	private static <T>boolean deepEquals(List<T> list1, List<T> list2) {
		if(list1.size() != list2.size())
			return false;
		
		for(int i = 0;i<list1.size();i++) {
			if(! list1.get(i).equals(list2.get(i)))
				return false;
		}
		return true;
	}

	private static <T> List<List<T>> getAllPossibleList(BinaryTreeNode<T> head) {
		ArrayList<LinkedList<T>> arrayList = new ArrayList<LinkedList<T>>();
		createLinkedListForEachLevel(head, arrayList, 0);

		List<List<List<T>>> listOfListOfList = new ArrayList<List<List<T>>>();

		// While creating a LoLoL make sure you understand which component do
		// you want to permute over
		// And which component ordering do you want to maintain.
		for (int i = 0; i < arrayList.size(); i++) {
			createPermutationsForListOfList(null, arrayList.get(i), listOfListOfList, i);
		}		
		return weaveIntoListOfList(listOfListOfList);
	}
	
	private static <T> List<List<T>> weaveIntoListOfList(List<List<List<T>>> listOfListOfList) {
		List<List<T>> listofList = new ArrayList<List<T>>();
		for (int i = 0; i < listOfListOfList.size(); i++) {
			if (i == 0) {
				listofList.add(new ArrayList<T>());
			}
			int sizeTillNow = listofList.size();
			for (int j = 1; j < listOfListOfList.get(i).size(); j++) {
				for (int k = 0; k < sizeTillNow; k++) {
					listofList.add(new ArrayList<T>(listofList.get(k)));
				}
			}
			int currentIndex = 0;
			for (int j = 0; j < listOfListOfList.get(i).size(); j++) {
				for(; currentIndex < sizeTillNow*(j+1); currentIndex++) {
					listofList.get(currentIndex).addAll(listOfListOfList.get(i).get(j));
				}
			}
		}
		return listofList;
	}

	private static <T> void createPermutationsForListOfList(LinkedList<T> prefix, LinkedList<T> linkedList,
			List<List<List<T>>> listOfListOfList, int index) {
		// TODO Auto-generated method stub
		if (linkedList.size() == 0) {
			checkArgument(listOfListOfList.size() >= index - 1);
			if (listOfListOfList.size() <= index) {
				listOfListOfList.add(new ArrayList<List<T>>());
			}
			listOfListOfList.get(index).add(prefix);
			return;
		}

		for (int i = 0; i < linkedList.size(); i++) {
			LinkedList<T> newPrefix = new LinkedList<T>();
			if (prefix != null) {
				newPrefix.addAll(prefix);
			}
			newPrefix.add(linkedList.get(i));

			LinkedList<T> newList = new LinkedList<T>();
			for (int y = 0; y < linkedList.size(); y++) {
				if (y == i) {// skip
					continue;
				}
				newList.add(linkedList.get(y));
			}

			createPermutationsForListOfList(newPrefix, newList, listOfListOfList, index);
		}
	}

	// private static <T> void createPermutationsForListOfList(List<List<T>>
	// prefix, List<List<T>> arrayList,
	// List<List<List<T>>> listOfListOfList) {
	// if(arrayList == null) {
	// listOfListOfList.add(prefix);
	// return;
	// }
	// for(int i = 0; i<arrayList.size(); i++) {
	// List<List<T>> pre = new ArrayList<List<T>>();
	// if(prefix != null) {
	// pre.addAll(prefix);
	// }
	// pre.add(arrayList.get(i));
	// List<List<T>> pro = new ArrayList<List<T>>();
	//
	// createPermutationsForListOfList(pre, )
	// }
	// }

	// Is t2 a subtree of t1
	private static <T> boolean isSubTree(BinaryTreeNode<T> t1, BinaryTreeNode<T> t2) {
		if (t2 == null)
			return true;
		if (t1 == null)
			return false;
		if (t1.data.equals(t2.data) && matchTree(t1, t2))
			return true;
		return isSubTree(t1.left, t2) || isSubTree(t1.right, t2);
	}

	private static <T> boolean matchTree(BinaryTreeNode<T> t1, BinaryTreeNode<T> t2) {
		if (t1 == null && t2 == null)
			return true;
		if (t1 == null || t2 == null)
			return false;
		if (!t1.data.equals(t2.data))
			return false;
		return matchTree(t1.left, t2.left) && matchTree(t1.right, t2.right);
	}

	private static void question10() {
		System.out.println("Q10. Given a large tree T1 and a small tree T2, check if T2 is a subtree of T1");
		solution10();
	}

	private static void solution10() {
		BinaryTreeNode<String> t1 = new BinaryTreeNode<String>("T1-H");
		BinaryTreeNode<String> t2 = new BinaryTreeNode<String>("T2-H");

		checkArgument(!isSubTree(t1, t2));

		t1 = t2;
		checkArgument(isSubTree(t1, t2));

		BinaryTreeNode<Integer> tr1 = new BinaryTreeNode<Integer>(1);
		BinaryTreeNode<Integer> tr2 = new BinaryTreeNode<Integer>(1);

		tr1.left = new BinaryTreeNode<Integer>(0);
		tr1.left.left = new BinaryTreeNode<Integer>(-1);
		tr1.left.right = new BinaryTreeNode<Integer>(1);

		checkArgument(isSubTree(tr1.left.right, tr2));

		tr2 = new BinaryTreeNode<Integer>(0);
		tr2.left = new BinaryTreeNode<Integer>(-1);
		tr2.right = new BinaryTreeNode<Integer>(1);

		checkArgument(isSubTree(tr1.left, tr2));
	}

	private static void question11() {
		System.out.println("Q11. Create a Class to represent a binary search tree, the class should support insert, "
				+ "find, delete and getRandomNode()");
		BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<Integer>();
		binarySearchTree.insert(4);
		binarySearchTree.insert(1);
		binarySearchTree.insert(1);
		binarySearchTree.insert(2);
		binarySearchTree.insert(5);
		binarySearchTree.insert(3);
		binarySearchTree.insert(6);
		List<Integer> list = binarySearchTree.getList();

		checkArgument(list.get(0).equals(1));
		checkArgument(list.get(1).equals(1));
		checkArgument(list.get(2).equals(2));
		checkArgument(list.get(3).equals(3));
		checkArgument(list.get(4).equals(4));
		checkArgument(list.get(5).equals(5));
		checkArgument(list.get(6).equals(6));

		checkArgument(binarySearchTree.delete(1));
		checkArgument(!binarySearchTree.delete(7));
		checkArgument(binarySearchTree.delete(3));
		list = binarySearchTree.getList();

		checkArgument(list.get(0).equals(1));
		checkArgument(list.get(1).equals(2));
		checkArgument(list.get(2).equals(4));
		checkArgument(list.get(3).equals(5));
		checkArgument(list.get(4).equals(6));

		checkArgument(binarySearchTree.find(2));
		checkArgument(!binarySearchTree.find(20));

		while (binarySearchTree.getRandom().equals(1)) {
		}
		while (binarySearchTree.getRandom().equals(2)) {
		}
		while (binarySearchTree.getRandom().equals(4)) {
		}
		while (binarySearchTree.getRandom().equals(5)) {
		}
		while (binarySearchTree.getRandom().equals(6)) {
		}
	}

	// TODO - I don't understand the solution here
	private static void question12() {
		System.out.println("Q12. Given a binary tree, return a list of all paths that lead to a given value.");

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

	private static <T> void createLinkedListForEachLevel(BinaryTreeNode<T> node, ArrayList<LinkedList<T>> arrayList,
			int depth) {
		if (node == null)
			return;
		while (arrayList.size() <= depth)
			arrayList.add(new LinkedList<T>());
		arrayList.get(depth).add(node.data);
		createLinkedListForEachLevel(node.left, arrayList, depth + 1);
		createLinkedListForEachLevel(node.right, arrayList, depth + 1);
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
		checkArgument(isBinarySearchTree(head));

		head.left = new BinaryTreeNode<Integer>(2);
		checkArgument(!isBinarySearchTree(head));

		head.left = new BinaryTreeNode<Integer>(0);
		checkArgument(isBinarySearchTree(head));

		head.right = new BinaryTreeNode<Integer>(0);
		checkArgument(!isBinarySearchTree(head));

		head.right = new BinaryTreeNode<Integer>(2);
		checkArgument(isBinarySearchTree(head));

		head.right.left = new BinaryTreeNode<Integer>(1);
		checkArgument(!isBinarySearchTree(head));

		// left node is deleted here since we are using the new operator
		head.right = new BinaryTreeNode<Integer>(3);
		checkArgument(isBinarySearchTree(head));

		head.right.left = new BinaryTreeNode<Integer>(2);
		checkArgument(isBinarySearchTree(head));

		// =================Test 2 ==================

		head = new BinaryTreeNode<Integer>(5);
		head.left = new BinaryTreeNode<Integer>(2);
		head.left.left = new BinaryTreeNode<Integer>(1);
		head.left.right = new BinaryTreeNode<Integer>(4);
		head.left.right.left = new BinaryTreeNode<Integer>(3);
		head.left.right.left.left = new BinaryTreeNode<Integer>(2);

		head.right = new BinaryTreeNode<Integer>(8);
		head.right.left = new BinaryTreeNode<Integer>(6);
		head.right.right = new BinaryTreeNode<Integer>(9);
		head.right.left.right = new BinaryTreeNode<Integer>(7);

		checkArgument(isBinarySearchTree(head));
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
		if ((maxValue != null && node.data.compareTo(maxValue) >= 0)
				|| (minValue != null && node.data.compareTo(minValue) < 0))
			return false;

		return isBinarySearchTreeHelper(node.left, node.data, minValue)
				&& isBinarySearchTreeHelper(node.right, maxValue, node.data);
	}

	private static void question6() {
		System.out.println("Q6. Given a BST find the next node in order of succession. ");
		solution6();
	}

	private static void solution6() {
		CustomBinaryTreeNode<Long> head = new CustomBinaryTreeNode<Long>(5L, null);
		head.left = new CustomBinaryTreeNode<Long>(2L, head);
		head.left.left = new CustomBinaryTreeNode<Long>(1L, head.left);
		head.left.right = new CustomBinaryTreeNode<Long>(4L, head.left);
		head.left.right.left = new CustomBinaryTreeNode<Long>(3L, head.left.right);
		head.left.right.left.left = new CustomBinaryTreeNode<Long>(2L, head.left.right.left);

		head.right = new CustomBinaryTreeNode<Long>(8L, head);
		head.right.left = new CustomBinaryTreeNode<Long>(6L, head.right);
		head.right.right = new CustomBinaryTreeNode<Long>(9L, head.right);
		head.right.left.right = new CustomBinaryTreeNode<Long>(7L, head.right.left);

		// Special case
		checkArgument(findNextGreaterOrEqualElement(head.left.right.left.left).equals(3L));

		checkArgument(findNextGreaterOrEqualElement(head.left.left).equals(2L));
		checkArgument(findNextGreaterOrEqualElement(head.left).equals(2L));
		checkArgument(findNextGreaterOrEqualElement(head.left.right.left).equals(4L));
		checkArgument(findNextGreaterOrEqualElement(head.left.right).equals(5L));
		checkArgument(findNextGreaterOrEqualElement(head).equals(6L));
		checkArgument(findNextGreaterOrEqualElement(head.right.left).equals(7L));
		checkArgument(findNextGreaterOrEqualElement(head.right.left.right).equals(8L));
		checkArgument(findNextGreaterOrEqualElement(head.right).equals(9L));
		checkArgument(findNextGreaterOrEqualElement(head.right.right) == null);

		// Special case
		checkArgument(findNextLesserOrEqualElement(head.left.right.left.left).equals(2L));

		checkArgument(findNextLesserOrEqualElement(head.left.left) == null);
		checkArgument(findNextLesserOrEqualElement(head.left).equals(1L));
		checkArgument(findNextLesserOrEqualElement(head.left.right.left).equals(2L));
		checkArgument(findNextLesserOrEqualElement(head.left.right).equals(3L));
		checkArgument(findNextLesserOrEqualElement(head).equals(4L));
		checkArgument(findNextLesserOrEqualElement(head.right.left).equals(5L));
		checkArgument(findNextLesserOrEqualElement(head.right.left.right).equals(6L));
		checkArgument(findNextLesserOrEqualElement(head.right).equals(7L));
		checkArgument(findNextLesserOrEqualElement(head.right.right).equals(8L));
	}

	private static <T extends Comparable<T>> T findNextLesserOrEqualElement(CustomBinaryTreeNode<T> node) {
		checkNotNull(node);
		return (node.left == null) ? findLesserParent(node) : findRightChild(node.left);
	}

	private static <T extends Comparable<T>> T findRightChild(CustomBinaryTreeNode<T> node) {
		checkNotNull(node);
		return (node.right == null) ? node.data : findRightChild(node.right);
	}

	private static <T extends Comparable<T>> T findLesserParent(CustomBinaryTreeNode<T> node) {
		checkNotNull(node);
		if (node.parent == null)
			return null;
		return (node.parent.data.compareTo(node.data) < 0) ? node.parent.data : findLesserParent(node.parent);
	}

	private static <T extends Comparable<T>> T findNextGreaterOrEqualElement(CustomBinaryTreeNode<T> node) {
		checkNotNull(node);
		return (node.right == null) ? findGreaterParent(node) : findLeftChild(node.right);
	}

	private static <T extends Comparable<T>> T findGreaterParent(CustomBinaryTreeNode<T> node) {
		checkNotNull(node);
		if (node.parent == null)
			return null;
		return (node.parent.data.compareTo(node.data) >= 0) ? node.parent.data : findGreaterParent(node.parent);
	}

	// TODO - Document that special care for null needs to be taken in recursion
	// - hence last statement needed change
	private static <T extends Comparable<T>> T findLeftChild(CustomBinaryTreeNode<T> node) {
		checkNotNull(node);
		return (node.left == null) ? node.data : findLeftChild(node.left);
	}

	private static void question7() {
		System.out.println(
				"Q7. Given a list of jobs and their dependencies, create a build order or return an error if no order can be created");
		System.out.println("\tprojects: a, b, c, d, e, f");
		System.out.println("\t(a, d), (f, b), (b, d), (f,a), (d, c)");
		System.out.println("\toutput: f, e, a. b, d, c");

		// To solve this we check if the graph has cyclic dependency
		// If not then we use topological sort logic (DFS)
		// https://www.youtube.com/watch?v=ddTC4Zovtbc
		// https://www.youtube.com/watch?v=rKQaZuoUR4M
		solution7();
	}

	private static void solution7() {
		// A depends on B and C
		// B depends on C
		GraphNode<String> a = new GraphNode<String>("A");
		GraphNode<String> b = new GraphNode<String>("B");
		GraphNode<String> c = new GraphNode<String>("C");
		a.incomingNodes.add(b);
		a.incomingNodes.add(c);
		b.incomingNodes.add(c);
		Graph<String> g = new Graph<String>();
		g.add(a);
		g.add(b);
		g.add(c);
		Stack<GraphNode<String>> order = orderTopologically(g);
		checkArgument(order.pop() == c);
		checkArgument(order.pop() == b);
		checkArgument(order.pop() == a);

		// Adding D as an independent node
		g.reset();
		GraphNode<String> d = new GraphNode<String>("D");
		g.add(d);
		order = orderTopologically(g);

		checkArgument(order.pop() == c);
		checkArgument(order.pop() == b);
		checkArgument(order.pop() == a);
		checkArgument(order.pop() == d);

		// Making D depend on A and C
		g.reset();
		d.incomingNodes.add(a);
		d.incomingNodes.add(c);
		order = orderTopologically(g);

		checkArgument(order.pop() == c);
		checkArgument(order.pop() == b);
		checkArgument(order.pop() == a);
		checkArgument(order.pop() == d);

		// Making cyclic dependency A depends on D
		g.reset();
		a.incomingNodes.add(d);
		order = orderTopologically(g);

		checkArgument(order == null);
	}

	private static <T> Stack<GraphNode<T>> orderTopologically(Graph<T> g) {
		Stack<GraphNode<T>> stack = new Stack<GraphNode<T>>();
		for (GraphNode<T> node : g.nodes) {
			if (node.state == GraphState.START && !doDFS(node, stack))
				return null;
		}
		return stack.reverse();
	}

	private static <T> boolean doDFS(GraphNode<T> node, Stack<GraphNode<T>> stack) {
		if (node.state == GraphState.INPROGRESS)
			return false;
		if (node.state == GraphState.COMPLETE)
			return true;

		checkArgument(node.state == GraphState.START);

		node.state = GraphState.INPROGRESS;
		for (GraphNode<T> n : node.incomingNodes) {
			if (!doDFS(n, stack))
				return false;
		}
		node.state = GraphState.COMPLETE;
		stack.push(node);
		return true;
	}

	private static void question8() {
		System.out.println("Q8. Given two nodes, find the least common ancestor in a BT.");
		// Using DFS and throw (a pogo class return value could also be used)
		solution8_1();

		// IF there is a link to the parent
		// Less space
		solution8_2();

		// Less computation
		solution8_3();
	}

	private static void solution8_3() {
		CustomBinaryTreeNode<Integer> head = new CustomBinaryTreeNode<Integer>(5, null);
		head.left = new CustomBinaryTreeNode<Integer>(2, head);
		head.left.left = new CustomBinaryTreeNode<Integer>(1, head.left);
		head.left.right = new CustomBinaryTreeNode<Integer>(4, head.left);
		head.left.right.left = new CustomBinaryTreeNode<Integer>(3, head.left.right);
		head.left.right.left.left = new CustomBinaryTreeNode<Integer>(2, head.left.right.left);

		head.right = new CustomBinaryTreeNode<Integer>(8, head);
		head.right.left = new CustomBinaryTreeNode<Integer>(6, head.right);
		head.right.right = new CustomBinaryTreeNode<Integer>(9, head.right);
		head.right.left.right = new CustomBinaryTreeNode<Integer>(7, head.right.left);

		checkArgument(commonAncestor(head.left, head.right) == head);
		checkArgument(commonAncestor(head.left, head.left.right) == head.left);
		checkArgument(commonAncestor(head.right.left.right, head.right.right) == head.right);
	}

	private static <T> CustomBinaryTreeNode commonAncestor(CustomBinaryTreeNode<T> node1,
			CustomBinaryTreeNode<T> node2) {
		Stack<CustomBinaryTreeNode<T>> stack1 = new Stack<CustomBinaryTreeNode<T>>();
		Stack<CustomBinaryTreeNode<T>> stack2 = new Stack<CustomBinaryTreeNode<T>>();

		while (node1 != null) {
			stack1.push(node1);
			node1 = node1.parent;
		}

		while (node2 != null) {
			stack2.push(node2);
			node2 = node2.parent;
		}

		CustomBinaryTreeNode<T> temp = null;

		while (stack1.peek() == stack2.peek()) {
			temp = stack1.pop();
			stack2.pop();
		}

		return temp;
	}

	private static void solution8_2() {
		CustomBinaryTreeNode<Integer> head = new CustomBinaryTreeNode<Integer>(5, null);
		head.left = new CustomBinaryTreeNode<Integer>(2, head);
		head.left.left = new CustomBinaryTreeNode<Integer>(1, head.left);
		head.left.right = new CustomBinaryTreeNode<Integer>(4, head.left);
		head.left.right.left = new CustomBinaryTreeNode<Integer>(3, head.left.right);
		head.left.right.left.left = new CustomBinaryTreeNode<Integer>(2, head.left.right.left);

		head.right = new CustomBinaryTreeNode<Integer>(8, head);
		head.right.left = new CustomBinaryTreeNode<Integer>(6, head.right);
		head.right.right = new CustomBinaryTreeNode<Integer>(9, head.right);
		head.right.left.right = new CustomBinaryTreeNode<Integer>(7, head.right.left);
		checkArgument(solution8_2_helper(head.left, head.right).equals("5"));
		checkArgument(solution8_2_helper(head.left, head.left.right).equals("2"));
		checkArgument(solution8_2_helper(head.right.left.right, head.right.right).equals("8"));
	}

	// TODO - Optimize this to make the run time O(N) instead of O(N ^ 2)
	private static <T> String solution8_2_helper(CustomBinaryTreeNode<T> child1, CustomBinaryTreeNode<T> child2) {
		checkArgument(child2 != null);
		if (child1 == null)
			return null;
		if (containsNode(child1, child2))
			return child1.data.toString();
		return solution8_2_helper(child1.parent, child2);
	}

	private static <T> boolean containsNode(CustomBinaryTreeNode<T> node, CustomBinaryTreeNode<T> child) {
		checkNotNull(child);
		if (node == null)
			return false;
		if (node == child)
			return true;
		return containsNode(node.left, child) || containsNode(node.right, child);
	}

	private static void solution8_1() {
		// TODO Auto-generated method stub
		BinaryTreeNode<Integer> head = new BinaryTreeNode<Integer>(5);
		head.left = new BinaryTreeNode<Integer>(2);
		head.left.left = new BinaryTreeNode<Integer>(1);
		head.left.right = new BinaryTreeNode<Integer>(4);
		head.left.right.left = new BinaryTreeNode<Integer>(3);
		head.left.right.left.left = new BinaryTreeNode<Integer>(2);

		head.right = new BinaryTreeNode<Integer>(8);
		head.right.left = new BinaryTreeNode<Integer>(6);
		head.right.right = new BinaryTreeNode<Integer>(9);
		head.right.left.right = new BinaryTreeNode<Integer>(7);

		checkArgument(solution8_1_helper(head, head.left, head.right).equals("5"));
		checkArgument(solution8_1_helper(head, head.left, head.left.right).equals("2"));
		checkArgument(solution8_1_helper(head, head.right.left.right, head.right.right).equals("8"));
	}

	private static <T> String solution8_1_helper(BinaryTreeNode<T> node, BinaryTreeNode<T> child1,
			BinaryTreeNode<T> child2) {
		checkArgument(child1 != null && child2 != null);
		if (child1 == child2)
			return child1.data.toString();
		try {
			getCommonAncestor(node, child1, child2);
			return null;
		} catch (CustomException ex) {
			return ex.getMessage();
		}
	}

	private static <T> boolean getCommonAncestor(BinaryTreeNode<T> node, BinaryTreeNode<T> child1,
			BinaryTreeNode<T> child2) throws CustomException {
		if (node == null)
			return false;
		boolean left = getCommonAncestor(node.left, child1, child2);
		boolean right = getCommonAncestor(node.right, child1, child2);
		if (node == child1 || node == child2) {
			if (left || right)
				throw new CustomException(node.data.toString());
			return true;
		}
		if (left && right)
			throw new CustomException(node.data.toString());
		return left || right;
	}

	private static void extraQuestion1() {
		System.out.println("Extra: Convert the binary search tree into an array.");
		extraSolution1();
	}

	private static void extraSolution1() {
		ArrayList list = new ArrayList<String>();
		BinaryTreeNode<String> head = new BinaryTreeNode<String>("H");
		head.left = new BinaryTreeNode<String>("HL");
		head.right = new BinaryTreeNode<String>("HR");
		head.left.left = new BinaryTreeNode<String>("HLL");
		head.left.right = new BinaryTreeNode<String>("HLR");
		head.right.left = new BinaryTreeNode<String>("HRL");
		head.right.right = new BinaryTreeNode<String>("HRR");
		head.right.right.right = new BinaryTreeNode<String>("HRRR");
		head.right.left.left = new BinaryTreeNode<String>("HRLL");

		convertBSTToArrayList(head, list);
		checkArgument(list.get(0).equals(head.left.left.data));
		checkArgument(list.get(1).equals(head.left.data));
		checkArgument(list.get(2).equals(head.left.right.data));
		checkArgument(list.get(3).equals(head.data));
		checkArgument(list.get(4).equals(head.right.left.left.data));
		checkArgument(list.get(5).equals(head.right.left.data));
		checkArgument(list.get(6).equals(head.right.data));
		checkArgument(list.get(7).equals(head.right.right.data));
		checkArgument(list.get(8).equals(head.right.right.right.data));

		BinaryTreeNode<Integer> head2 = new BinaryTreeNode<Integer>(5);
		head2.left = new BinaryTreeNode<Integer>(2);
		head2.left.left = new BinaryTreeNode<Integer>(1);
		head2.left.right = new BinaryTreeNode<Integer>(4);
		head2.left.right.left = new BinaryTreeNode<Integer>(3);
		head2.left.right.left.left = new BinaryTreeNode<Integer>(2);

		head2.right = new BinaryTreeNode<Integer>(8);
		head2.right.left = new BinaryTreeNode<Integer>(6);
		head2.right.right = new BinaryTreeNode<Integer>(9);
		head2.right.left.right = new BinaryTreeNode<Integer>(7);

		ArrayList<Integer> list2 = new ArrayList<Integer>();
		convertBSTToArrayList(head2, list2);
		checkArgument(list2.get(0) == 1);
		checkArgument(list2.get(1) == 2);
		checkArgument(list2.get(2) == 2);
		checkArgument(list2.get(3) == 3);
		checkArgument(list2.get(4) == 4);
		checkArgument(list2.get(5) == 5);
		checkArgument(list2.get(6) == 6);
		checkArgument(list2.get(7) == 7);
		checkArgument(list2.get(8) == 8);
		checkArgument(list2.get(9) == 9);
	}

	// Using In order traversal
	private static <T> void convertBSTToArrayList(BinaryTreeNode<T> node, ArrayList<T> list) {
		if (node == null)
			return;

		convertBSTToArrayList(node.left, list);
		list.add(node.data);
		convertBSTToArrayList(node.right, list);
	}

}

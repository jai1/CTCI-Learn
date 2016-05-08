package org.ctci.utils;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BinarySearchTree<T extends Comparable<T>> {
	BinaryTreeNode<T> head;
	Random rn = new Random();
	int size = 0;

	public void insert(T data) {
		if (size == 0)
			head = new BinaryTreeNode<T>(data);
		else
			insertNode(head, data);
		size++;
	}

	private void insertNode(BinaryTreeNode<T> node, T data) {
		checkNotNull(node);
		if (data.compareTo(node.data) < 0) {
			if (node.left == null) {
				node.left = new BinaryTreeNode<T>(data);
			} else {
				insertNode(node.left, data);
			}
		} else {
			if (node.right == null) {
				node.right = new BinaryTreeNode<T>(data);
			} else {
				insertNode(node.right, data);
			}
		}
	}

	public boolean find(T data) {
		 return findHelper(head, data);
	}
	
	private boolean findHelper(BinaryTreeNode<T> node, T data) {
		if(node == null)
			return false;
		if(node.data.equals(data))
			return true;
		return findHelper(node.left, data) || findHelper(node.right, data);
	}

	public boolean delete(T data) {
		boolean dataFound = find(data);
		head = deleteNode(head, data);
		if(dataFound)
			size--;
		return dataFound;
	}

	private BinaryTreeNode<T> deleteNode(BinaryTreeNode<T> node, T data) {
		if (node == null)
			return null;
		if (data.compareTo(node.data) < 0)
			node.left = deleteNode(node.left, data);
		else if (data.compareTo(node.data) > 0)
			node.right = deleteNode(node.right, data);
		else { // data == node.data
			if (node.left == null)
				node = node.right;
			else if (node.right == null)
				node = node.left;
			else {
				// Both nodes exist
				// could have also used findMax(node.left)
				BinaryTreeNode<T> temp = findMin(node.right);
				node.data = temp.data;
				node.right = deleteNode(node.right, temp.data);
			}
		}
		return node;
	}

	public BinaryTreeNode<T> findMin(BinaryTreeNode<T> node) {
		if (node == null)
			return null;
		if (node.left == null)
			return node;
		return findMin(node.left);
	}

	public BinaryTreeNode<T> findMax(BinaryTreeNode<T> node) {
		if (node == null)
			return null;
		while (node.right != null) {
			node = node.right;
		}
		return node;
	}

	public List<T> getList() {
		ArrayList<T> list = new ArrayList<T>();
		convertBSTToArrayList(head, list);
		return list;
	}

	private static <T> void convertBSTToArrayList(BinaryTreeNode<T> node, ArrayList<T> list) {
		if (node == null)
			return;
		convertBSTToArrayList(node.left, list);
		list.add(node.data);
		convertBSTToArrayList(node.right, list);
	}
	
	public T getRandom() {
		if(size == 0)
			return null;
		int randInt = randomInteger(1, size);
		randInt -- ;
		List<T> list = getList();
		return list.get(randInt);
	}


	public int size() {
		int size = getSubTreeSize(head);
		checkArgument(size == this.size);
		return size;
	}
	
	private int getSubTreeSize(BinaryTreeNode<T> node) {
		if(node == null)
			return 0;
		return getSubTreeSize(node.left) + getSubTreeSize(node.right) + 1;
	}

	private int randomInteger(int min, int max) {
		int n = max - min + 1;
		int i = rn.nextInt() % n;
		if(i<0)
			i *= -1;
		return  min + i;
	}

	public void insertAll(List<T> list) {
		for(T data : list) {
			insert(data);
		}
	}
	
	public boolean equals(BinarySearchTree<T> tree) {
		if(this.size != tree.size())
			return false;

		return this.head.deepEquals(tree.head);
	}

}

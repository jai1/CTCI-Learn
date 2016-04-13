package org.asher.utils;

public class BinaryTreeNode<T> {
	public BinaryTreeNode<T> left = null;
	public BinaryTreeNode<T> right = null;
	public T data = null;

	public BinaryTreeNode() {
	}

	public BinaryTreeNode(T temp) {
		data = temp;
	}

	public boolean equals(LinkedListNode<T> n) {
		return n != null && data.equals(n.data);
	}
	
	public String toString(){
		return data.toString();
	}
}

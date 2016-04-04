package org.asher.utils;

public class LinkedListNode<T> {
	public LinkedListNode<T> next = null;
	public T data = null;

	public LinkedListNode() {
	}

	public LinkedListNode(T temp) {
		data = temp;
	}

	public boolean equals(LinkedListNode<T> n) {
		return n != null && data.equals(n.data);
	}
}
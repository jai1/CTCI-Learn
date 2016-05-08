package org.ctci.utils;

public class Stack<T> implements StackInterface<T> {
	protected LinkedListNode<T> head = null;

	public LinkedListNode<T> getHead() {
		return head;
	}

	public int size() {
		int size = 0;
		LinkedListNode<T> node = head;
		while (node != null) {
			size += 1;
			node = node.next;
		}
		return size;
	}

	public T peek() {
		if (head == null)
			return null;
		return head.data;
	}

	public T pop() {
		if (head == null)
			return null;
		T data = head.data;
		head = head.next;
		return data;
	}

	public void push(T data) {
		LinkedListNode<T> node = new LinkedListNode<T>(data);
		node.next = head;
		head = node;
	}

	public boolean isEmpty() {
		return (head == null);
	}

	public String toString() {
		StringBuilder build = new StringBuilder();
		LinkedListNode<T> node = head;
		while (node != null) {
			if (node.data == null)
				build.append("| ").append("NULL").append(" |");
			else
				build.append("| ").append(node.data.toString()).append(" |");
			build.append("->");
			node = node.next;
		}
		build.append("| ").append("NULL").append(" |");
		return build.toString();
	}

	public Stack<T> reverse() {
		LinkedListNode<T> prev = null;
		LinkedListNode<T> node = head;
		LinkedListNode<T> next = null;

		while (node != null) {
			next = node.next;
			node.next = prev;
			prev = node;
			head = node;
			node = next;
		}
		return this;
	}
}

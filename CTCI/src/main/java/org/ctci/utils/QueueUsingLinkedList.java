package org.ctci.utils;

import static com.google.common.base.Preconditions.checkArgument;

public class QueueUsingLinkedList<T> implements QueueInterface<T> {
	private LinkedListNode<T> head = null;
	private LinkedListNode<T> tail = null;
	// remove and peek from head - O(1)
	// add at tail - O(1)
	// other way around makes deletion O(N)

	public int size() {
		int size = 0;
		LinkedListNode<T> node = head;
		while (node != null) {
			size += 1;
			node = node.next;
		}
		return size;
	}

	public void add(T data) {
		if (tail == null) {
			checkArgument(head == null);
			head = new LinkedListNode<T>(data);
			tail = head;
		}
		LinkedListNode<T> node = new LinkedListNode<T>(data);
		tail.next = node;
		tail = tail.next;
	}

	public T remove() {
		// 0 elements
		if (head == null) {
			checkArgument(tail == null);
			return null;
		}

		T data = head.data;
		head = head.next;

		if (head == null)
			tail = null;
		return data;
	}

	public T peek() {
		// 0 elements
		if (head == null) {
			checkArgument(tail == null);
			return null;
		}

		T data = head.data;
		return data;
	}
	
	public boolean isEmpty() {
		return (head == null);
	}
};
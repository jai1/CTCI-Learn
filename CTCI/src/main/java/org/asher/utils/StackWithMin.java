package org.asher.utils;

import static com.google.common.base.Preconditions.checkArgument;

public class StackWithMin<T extends Comparable<T>> extends Stack<T> {

	public T min() {
		if (head == null)
			return null;
		return ((LinkedListNodeWithMin<T>)head).min;
	}

	public void push(T data) {
		if (data == null)
			throw new IllegalArgumentException("Can't insert null data");
		if (head == null) {
			head = new LinkedListNodeWithMin<T>(data, data);
			return;
		}
		T min = ((LinkedListNodeWithMin<T>)head).min;
		checkArgument(min != null);
		min = (min.compareTo(data) < 0) ? min : data;
		LinkedListNodeWithMin<T> node = new LinkedListNodeWithMin<T>(data, min);
		node.next = head;
		head = node;
	}

	class LinkedListNodeWithMin<T> extends LinkedListNode<T> {
		T min = null;

		LinkedListNodeWithMin(T element, T min) {
			super(element);
			this.min = min;
		}
	}
}

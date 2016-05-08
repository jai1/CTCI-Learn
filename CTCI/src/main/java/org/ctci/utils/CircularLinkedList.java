package org.ctci.utils;

import java.util.Iterator;

// 
public class CircularLinkedList<T> implements Iterable<T>{
	T data;
	LinkedListNode<T> tail = null;
	int size = 0;

	public CircularLinkedList<T> add(T data) {
		addLast(data);
		return this;
	}

	public CircularLinkedList<T> addFirst(T data) {
		LinkedListNode<T> temp = new LinkedListNode<T>(data);
		if (tail == null) {
			tail = temp;
			tail.next = tail;
		} else {
			temp.next = tail.next;
			tail.next = temp;
		}
		size++;
		return this;
	}

	public CircularLinkedList<T> addLast(T data) {
		addFirst(data);
		tail = tail.next;
		return this;
	}

	public boolean remove(T data) {
		if (tail == null)
			return false;
		LinkedListNode<T> temp = tail;
		for (int i = 1; i < size; i++) {
			if (temp.next.data.equals(data)) {
				temp.next = temp.next.next;
				size--;
				return true;
			}
			temp = temp.next;
		}

		if (tail.data.equals(data)) {
			if (size == 1) {
				tail = null;
			} else {
				temp.next = tail.next;
				tail = temp;
			}
			size--;
			return true;
		}

		return false;
	}

	public int removeAll(T data) {
		int count = 0;
		while (remove(data))
			count++;
		return count;
	}

	public void removeFirst() {
		if (tail == null)
			return;
		if (size == 1) {
			tail = null;
		} else {
			tail.next = tail.next.next;
		}
		size--;
	}

	public void removeLast() {
		if (tail == null)
			return;
		for (int i = 1; i < size; i++)
			tail = tail.next;
		removeFirst();
	}

	public int size() {
		return size;
	}

	public T getNext() {
		if (tail == null)
			throw new ArrayIndexOutOfBoundsException("No data in the CircularLinkedList");
		tail = tail.next;
		return tail.data;
	}

	public T peekNext() {
		if (tail == null)
			throw new ArrayIndexOutOfBoundsException("No data in the CircularLinkedList");
		return tail.next.data;
	}

	public String toString() {
		String string = "";
		for (int i = 0; i < size; i++)
			string += "|" + getNext() + "| -> ";
		string += "| NULL |";
		return string;
	}
	
	public Iterator<T> iterator() {
		return new CustomIterator<T>();
	}
	
	class CustomIterator<T> implements Iterator<T> {
		private int index = 0;

		public boolean hasNext() {
			return index < size;
		}

		public T next() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}

package org.ctci.utils;

public interface StackInterface<T> {
	public int size();

	public T peek();

	public T pop();

	public void push(T data);

	public boolean isEmpty();

	public String toString();
}

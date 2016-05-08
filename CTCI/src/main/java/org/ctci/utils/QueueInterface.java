package org.ctci.utils;

public interface QueueInterface<T> {
	public int size();

	public void add(T data);

	public T remove();

	public T peek();
	
	public boolean isEmpty();
}

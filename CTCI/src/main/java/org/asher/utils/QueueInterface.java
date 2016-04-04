package org.asher.utils;

import static com.google.common.base.Preconditions.checkArgument;

public interface QueueInterface<T> {
	public int size();

	public void add(T data);

	public T remove();

	public T peek();
	
	public boolean isEmpty();
}

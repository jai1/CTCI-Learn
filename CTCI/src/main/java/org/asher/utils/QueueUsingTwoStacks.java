package org.asher.utils;

public class QueueUsingTwoStacks<T> implements QueueInterface<T>{
	private Stack<T> stack1 = new Stack<T>();
	private Stack<T> stack2 = new Stack<T>();

	public int size() {
		return stack1.size() + stack2.size();
	}

	public void add(T data) {
		stack1.push(data);
	}

	public T remove() {
		copyData();
		return stack2.pop();
	}

	private void copyData() {
		if (stack2.size() == 0) {
			int size = stack1.size();
			while (size-- != 0) 
				stack2.push(stack1.pop());
		}
	}

	public T peek() {
		copyData();
		return stack2.peek();
	}

	public boolean isEmpty() {
		return (stack1.isEmpty() && stack2.isEmpty());
	}
}

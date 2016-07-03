package org.ctci.utils;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.ArrayList;

public class StackWithPriorityLevels<T> {
	ArrayList<Stack<T>> stacks = null;

	public StackWithPriorityLevels(int initialCapacity) {
		this.stacks = new ArrayList<Stack<T>>(initialCapacity);
	}

	public StackWithPriorityLevels() {
		this.stacks = new ArrayList<Stack<T>>();
	}

	// priority 1 is highest
	public void push(int priority, T element) {
		checkArgument(priority > 0);
		stacks.ensureCapacity(priority);
		// since array index start from 0
		priority -= 1;
		// IMP:- ArrayList can't have holes :-(
		for(int i = stacks.size(); i<=priority; i++) {
			stacks.add(i, new Stack<T>());
		}
		stacks.get(priority).push(element);
	}

	public T pop(int priority) {
		checkArgument(priority > 0);
		if (priority > stacks.size())
			return null;
		return stacks.get(priority - 1).pop();
	}

	// Returns and deletes highest priority element
	public T pop() {
		for (Stack<T> stack : stacks) {
			if (!stack.isEmpty()) 
				return stack.pop();
		}
		return null;
	}

	public T peek(int priority) {
		checkArgument(priority > 0);
		if (priority > stacks.size())
			return null;
		return stacks.get(priority - 1).peek();
	}

	// Returns highest priority element
	public T peek() {
		for (Stack<T> stack : stacks) {
			if (!stack.isEmpty())
				return stack.peek();
		}
		return null;
	}

	public int size(int priority) {
		checkArgument(priority > 0);
		if (priority > stacks.size())
			return 0;
		return stacks.get(priority - 1).size();
	}

	public int size() {
		int size = 0;
		for (Stack<T> stack : stacks) {
			size += stack.size();
		}
		return size;
	}
	
	public int maxPriority() {
		return stacks.size();
	}

	public boolean isEmpty(int priority) {
		checkArgument(priority > 0);
		if (priority > stacks.size())
			return true;
		return stacks.get(priority - 1).isEmpty();
	}
	
	public LinkedListNode<T> getHead(int priority) {
		checkArgument(priority > 0);
		if (priority > stacks.size())
			return null;
		return stacks.get(priority - 1).getHead();
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for(int i=0; i<stacks.size();i++)
			builder.append(stacks.get(i)).append("\n=============================\n");
		return builder.toString();
	}
}

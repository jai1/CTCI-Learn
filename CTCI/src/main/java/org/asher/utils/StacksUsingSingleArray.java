package org.asher.utils;

import java.util.ArrayList;

import org.asher.utils.LinkedListNode;
import static com.google.common.base.Preconditions.checkArgument;

public class StacksUsingSingleArray<T> {
	protected LinkedListNode<T>[] array =  null;
	protected ArrayList<ExtendedStack<T>> stacks = null;
	
	public StacksUsingSingleArray() {}
	
	public StacksUsingSingleArray(int stackCount) {
		array = new LinkedListNode[stackCount];	
		stacks = new ArrayList<ExtendedStack<T>>(stackCount);
		for(int i = 0; i<stackCount;i++) {
			stacks.add(new ExtendedStack<T>());
		}
	}
	
	void ensureCapacity(int emptyIndex) {
		if(emptyIndex < array.length) 
			return;
		LinkedListNode<T>[] newArray = new LinkedListNode[array.length * 2];	
		for(int i=0; i<array.length; i++) 
			newArray[i] = array[i];
		array = newArray;
	}
	
	int findEmptyIndex() {
		int i = 0;
		for(; i<array.length && array[i] != null;i++){}
		return i;
	}
	
	public void push(int stackIndex, T element) {
		checkArgument(stackIndex < stacks.size());
		int index = findEmptyIndex();
		ensureCapacity(index);
		array[index] = new LinkedListNode<T>(element);
		stacks.get(stackIndex).pushNode(array[index]);
	}
	
	public T pop(int stackIndex) {
		checkArgument(stackIndex < stacks.size());
		LinkedListNode<T> node = stacks.get(stackIndex).popNode();
		if(node == null)
			return null;
		int index = findNode(node);
		checkArgument(index != -1);
		array[index] = null;
		return node.data;
	}
	
	public T peek(int stackIndex) {
		checkArgument(stackIndex < stacks.size());
		return stacks.get(stackIndex).peek();
	}

	private int findNode(LinkedListNode<T> node) {
		for(int i = 0; i<array.length;i++) {
			if(array[i] == node)
				return i;
		}
		return -1;
	}
	
	public String toString() {
		StringBuilder build = new StringBuilder();
		for(LinkedListNode<T> node : array) {
			if(node == null) 
				build.append("| ").append("NULL").append(" |");
			else	
				build.append("| ").append(node.data.toString()).append(" |");
			build.append("->");
		}
		build.append("| ").append("NULL").append(" |");
		build.append("\n");
		for(int i =0; i<stacks.size(); i++) {
			build.append("Stack ").append(i).append(":");
			build.append(stacks.get(i));
			build.append("\n");
		}
		return build.toString();
	}
	
	class ExtendedStack<T> extends Stack<T>{
		public void pushNode(LinkedListNode<T> node) {
			checkArgument(node != null);
			node.next = head;
			head = node;
		}
		
		public LinkedListNode<T> popNode() {
			LinkedListNode<T> node = head;
			super.pop();
			return node;
		}
		
		public LinkedListNode<T> peekNode() {
			return head;
		}
		
		public T pop() {
			throw new UnsupportedOperationException("Sorry, not allowed.");
		}

		public void push(T data) {
			throw new UnsupportedOperationException("Sorry, not allowed.");
		}	
	}
}

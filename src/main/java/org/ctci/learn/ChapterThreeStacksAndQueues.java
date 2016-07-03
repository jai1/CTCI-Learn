package org.ctci.learn;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.ArrayList;

import org.ctci.utils.LinkedListNode;
import org.ctci.utils.QueueUsingTwoStacks;
import org.ctci.utils.SortingOrder;
import org.ctci.utils.Stack;
import org.ctci.utils.StackInterface;
import org.ctci.utils.StackWithMin;
import org.ctci.utils.StackWithPriorityLevels;
import org.ctci.utils.StacksUsingSingleArray;

public class ChapterThreeStacksAndQueues {

	public static void run() {
		System.out.println("\n****\nRunning Chapter 3 - Stacks and Queues\n****");
		question1();
		question2();
		question3();
		question4();
		question5();
		question6();
	}

	private static void question1() {
		System.out.println("Q1. Implement n stacks using an array");
		StacksUsingSingleArray<Integer> stack = new StacksUsingSingleArray<Integer>(3);
		checkArgument(stack.pop(1) == null);
		checkArgument(stack.peek(1) == null);

		stack.push(2, 7);
		stack.push(2, null);
		checkArgument(stack.pop(2) == null);
		checkArgument(stack.peek(2).equals(7));
		checkArgument(stack.pop(2).equals(7));
		checkArgument(stack.pop(2) == null);
		checkArgument(stack.peek(2) == null);

		stack.push(2, 7);
		stack.push(0, 5);
		stack.push(1, 3);
		stack.push(1, 6);
		stack.push(0, 10);
		stack.push(0, 3);
		stack.pop(0);
		stack.push(0, 15);
		stack.push(2, 14);
		stack.push(1, 10);
		stack.push(0, 7);
		stack.pop(0);
		stack.push(2, 13);
		stack.push(0, 20);
		stack.push(0, 7);
		stack.pop(2);
		stack.pop(1);
		stack.pop(0);
		stack.push(1, 9);
		stack.push(0, 25);
		stack.push(2, 21);
		// System.out.println(stack);

		checkArgument(stack.peek(0).equals(25));
		checkArgument(stack.peek(1).equals(9));
		checkArgument(stack.peek(2).equals(21));
		// System.out.println(stack);

		checkArgument(stack.pop(0).equals(25));
		checkArgument(stack.peek(1).equals(9));
		checkArgument(stack.pop(2).equals(21));
		// System.out.println(stack);

		checkArgument(stack.peek(0).equals(20));
		checkArgument(stack.peek(1).equals(9));
		checkArgument(stack.peek(2).equals(14));
		// System.out.println(stack);

		stack.push(0, 25);
		stack.push(1, 12);
		stack.push(2, 21);
		// System.out.println(stack);

		stack.push(0, 30);
		stack.push(1, 15);
		stack.push(2, 28);
		// System.out.println(stack);

		stack.push(0, 35);
		stack.push(1, 18);
		stack.push(2, 35);
		// System.out.println(stack);

		checkArgument(stack.peek(0).equals(35));
		checkArgument(stack.peek(1).equals(18));
		checkArgument(stack.peek(2).equals(35));
	}

	private static void question2() {
		System.out.println("Q2. Create a Stack which gives min() in O(1).");

		// Compromising space in order to gain computation of 0(1)
		StackWithMin<Character> stack = new StackWithMin<Character>();
		checkArgument(stack.pop() == null);
		checkArgument(stack.peek() == null);
		checkArgument(stack.min() == null);

		stack.push('w');
		checkArgument(stack.peek().equals('w'));
		checkArgument(stack.min().equals('w'));

		stack.push('y');
		checkArgument(stack.peek().equals('y'));
		checkArgument(stack.min().equals('w'));

		stack.push('a');
		checkArgument(stack.peek().equals('a'));
		checkArgument(stack.min().equals('a'));

		stack.push('b');
		checkArgument(stack.peek().equals('b'));
		checkArgument(stack.min().equals('a'));

		checkArgument(stack.pop().equals('b'));
		checkArgument(stack.min().equals('a'));

		checkArgument(stack.pop().equals('a'));
		checkArgument(stack.min().equals('w'));

		checkArgument(stack.pop().equals('y'));
		checkArgument(stack.min().equals('w'));

		checkArgument(stack.pop().equals('w'));
		checkArgument(stack.min() == null);
	}

	private static void question3() {
		System.out.println(
				"Q3. Implement a Data structure to store stack of plates, where if the stack goes above capacity a new stack is created internally and destroyed if number of plates decreases. Implement push, pop, popAt methods like in normal stack.");

		class SetOfStacks<T> implements StackInterface<T> {
			StackWithPriorityLevels<T> stack = new StackWithPriorityLevels<T>();
			// final fields can be initiated in constructors
			final int maxSize;
			int currentLevel = 1; // level 1 to infinity

			public SetOfStacks(int maxSize) {
				this.maxSize = maxSize;
			}

			public int size() {
				return stack.size();
			}

			public T peek() {
				return stack.peek(currentLevel);
			}

			public T pop() {
				T data = stack.pop(currentLevel);
				if (currentLevel != 1 && stack.isEmpty(currentLevel)) {
					currentLevel--;
				}
				return data;
			}

			public void push(T data) {
				checkArgument(stack.size(currentLevel) <= maxSize);
				if (stack.size(currentLevel) == maxSize)
					currentLevel++;
				stack.push(currentLevel, data);
			}

			public boolean isEmpty() {
				return stack.isEmpty(currentLevel);
			}

			public String toString() {
				return stack.toString();
			}

			public T popAt(int level) {
				checkArgument(level >= 0 && level <= currentLevel);
				// since we increment current level in the push function itself
				if (stack.isEmpty(level))
					return null;

				// Ensuring that we have atleast one element
				T data = stack.pop(level);

				for(int i = level + 1; i<=currentLevel;i++) {
					LinkedListNode<T> head = stack.getHead(i);
					LinkedListNode<T> node = new LinkedListNode<T>(null);
					if(head != null) 
						node = new LinkedListNode<T>(head.data);
					
					if(head.next != null) {
						head.data = head.next.data;
						head.next = head.next.next;
					}
					stack.push(i-1, node.data);
				}
				return data;
			}
			

			public T peekAt(int level) {
				checkArgument(level >= 0 && level <= currentLevel);
				// since we increment current level in the push function itself
				
				return stack.peek(level);
			}
		}

		SetOfStacks<String> plateStack = new SetOfStacks<String>(3);

		checkArgument(plateStack.pop() == null);
		checkArgument(plateStack.peek() == null);
		checkArgument(plateStack.size() == 0);
		checkArgument(plateStack.isEmpty());

		plateStack.push("1-1");
		plateStack.push("1-2");
		plateStack.push("1-3");
		plateStack.push("2-1");
		plateStack.push("2-2");
		plateStack.push("2-3");
		plateStack.push("2-4");
		plateStack.push("2-5");
		checkArgument(plateStack.pop().equals("2-5"));
		checkArgument(plateStack.pop().equals("2-4"));
		checkArgument(plateStack.peek().equals("2-3"));
		checkArgument(plateStack.size() == 6);
		checkArgument(!plateStack.isEmpty());
		// System.out.println(plateStack);

		plateStack.push("3-1");
		plateStack.push("3-2");
		plateStack.push("3-3");
		checkArgument(plateStack.peek().equals("3-3"));
		checkArgument(plateStack.size() == 9);
		checkArgument(!plateStack.isEmpty());
		
		checkArgument(plateStack.peekAt(2).equals("2-3"));
		checkArgument(plateStack.peekAt(2).equals(plateStack.popAt(2)));
		checkArgument(plateStack.size() == 8);
		checkArgument(!plateStack.isEmpty());
	}

	private static void question4() {
		System.out.println("Q4. Implement queue using 2 stacks.");
		QueueUsingTwoStacks<Float> queue = new QueueUsingTwoStacks<Float>();
		queue.add(1.0f);
		queue.add(1.1f);
		queue.add(1.2f);

		checkArgument(queue.remove().equals(1.0f));
		checkArgument(queue.peek().equals(1.1f));

		queue.add(1.3f);
		checkArgument(queue.remove().equals(1.1f));
		checkArgument(queue.peek().equals(1.2f));
		checkArgument(queue.remove().equals(1.2f));
		checkArgument(queue.peek().equals(1.3f));
		checkArgument(!queue.isEmpty());
		checkArgument(queue.remove().equals(1.3f));
		checkArgument(queue.peek() == null);
		checkArgument(queue.remove() == null);
		checkArgument(queue.size() == 0);
		checkArgument(queue.isEmpty());
	}

	private static void question5() {
		System.out.println("Q5. Sort a stack using another stack.");
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(3);
		stack.push(3);
		stack.push(1);
		stack.push(5);
		stack.push(8);
		stack.push(9);

		sort(SortingOrder.ASCENDING, stack);

		checkArgument(stack.pop().equals(1));
		checkArgument(stack.pop().equals(3));
		checkArgument(stack.pop().equals(3));
		checkArgument(stack.pop().equals(5));
		checkArgument(stack.pop().equals(8));
		checkArgument(stack.peek().equals(9));
		checkArgument(stack.pop().equals(9));
		checkArgument(stack.pop() == null);
		checkArgument(stack.peek() == null);

		stack.push(null);
		stack.push(10);
		stack.push(null);
		stack.push(4);

		sort(SortingOrder.DESCENDING, stack);
		
		checkArgument(stack.peek().equals(10));
		checkArgument(stack.pop().equals(10));
		checkArgument(stack.peek().equals(4));
		checkArgument(stack.pop().equals(4));
		checkArgument(stack.pop() == null);
		checkArgument(stack.pop() == null);
		checkArgument(stack.pop() == null);
		checkArgument(stack.peek() == null);
		
		stack.push(null);
		stack.push(10);
		stack.push(null);
		stack.push(4);

		sort(SortingOrder.ASCENDING, stack);
		
		checkArgument(stack.pop() == null);
		checkArgument(stack.pop() == null);
		checkArgument(stack.peek().equals(4));
		checkArgument(stack.pop().equals(4));
		checkArgument(stack.peek().equals(10));
		checkArgument(stack.pop().equals(10));
		checkArgument(stack.pop() == null);
		checkArgument(stack.peek() == null);
	}

	private static <T extends Comparable> void sort(SortingOrder order, Stack<T> stack) {
		Stack<T> tempStack = new Stack<T>();
		Stack<T> nullStack = new Stack<T>();
		while (!stack.isEmpty()) {
			T data = stack.pop();

			if (data == null) {
				nullStack.push(data);
				continue;
			}

			while (sortingCondition(order, data, tempStack.peek())) {
				stack.push(tempStack.pop());
			}
			tempStack.push(data);
		}

		while (order.equals(SortingOrder.DESCENDING) && !nullStack.isEmpty()) {
			stack.push(nullStack.pop());
		}

		// System.out.println("tempStack: " + tempStack);
		while (!tempStack.isEmpty()) {
			stack.push(tempStack.pop());
		}

		while (order.equals(SortingOrder.ASCENDING) && !nullStack.isEmpty()) {
			stack.push(nullStack.pop());
		}

		//System.out.println("Stack: " + stack);
	}

	private static <T extends Comparable> boolean sortingCondition(SortingOrder order, T data, T peek) {
		checkArgument(data != null);
		return (peek != null && ((order.equals(SortingOrder.ASCENDING) && peek.compareTo(data) > 0)
				|| (order.equals(SortingOrder.DESCENDING) && peek.compareTo(data) < 0)));
	}

	enum AnimalType {
		CATS(0), DOGS(1);
		private final int value;

		private AnimalType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	private static void question6() {
		System.out.println("Q6. A zoo collects and sells dogs and cats. "
				+ "Create a stack to support push(T, AnimalType), pop(), pop(AnimalType), peek() and peek(AnimalType).");

		class AnimalStack<T> {
			protected ArrayList<Stack<T>> stacks = null;
			Stack<LinkedListNode<T>> stack = new Stack<LinkedListNode<T>>();
			int size = 10;

			public AnimalStack(int size) {
				this.size = size;
				stacks = new ArrayList<Stack<T>>(size);
				initializeStacks();
			}

			public AnimalStack() {
				stacks = new ArrayList<Stack<T>>(this.size);
				initializeStacks();
			}

			private void initializeStacks() {
				for (int i = 0; i < size; i++) {
					stacks.add(new Stack<T>());
				}
			}

			public void push(AnimalType type, T data) {
				checkArgument(type.getValue() < size);
				stacks.get(type.getValue()).push(data);
				stack.push(stacks.get(type.getValue()).getHead());
			}

			public T pop() {
				LinkedListNode<T> node = stack.pop();
				if (node == null)
					return null;
				for (Stack<T> stack : stacks) {
					if (stack.getHead() == node)
						stack.pop();
				}
				return node.data;
			}

			public T pop(AnimalType type) {
				checkArgument(type.getValue() < size);
				return stacks.get(type.getValue()).pop();
			}

			public T peek() {
				LinkedListNode<T> node = stack.peek();
				if (node == null)
					return null;
				return node.data;
			}

			public T peek(AnimalType type) {
				checkArgument(type.getValue() < size);
				return stacks.get(type.getValue()).peek();
			}
		}

		AnimalStack<String> stack = new AnimalStack();

		checkArgument(stack.peek() == null);
		checkArgument(stack.pop() == null);
		stack.push(AnimalType.DOGS, "dog-1");
		stack.push(AnimalType.DOGS, "Dog-2");
		checkArgument(stack.peek().equals("Dog-2"));
		checkArgument(stack.pop().equals("Dog-2"));
		stack.push(AnimalType.DOGS, "dog-2");
		stack.push(AnimalType.CATS, "Cat-1");
		checkArgument(stack.peek().equals("Cat-1"));
		checkArgument(stack.pop().equals("Cat-1"));
		checkArgument(stack.peek().equals("dog-2"));
		stack.push(AnimalType.CATS, "cat-2");
		checkArgument(stack.peek(AnimalType.DOGS).equals("dog-2"));
		stack.push(AnimalType.CATS, "cat-3");
		checkArgument(stack.peek(AnimalType.CATS).equals("cat-3"));
	}

}

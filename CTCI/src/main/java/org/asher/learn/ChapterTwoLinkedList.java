package org.asher.learn;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.spi.IIOServiceProvider;
import javax.print.attribute.Size2DSyntax;

import org.asher.utils.LinkedListNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChapterTwoLinkedList {
	private static final Logger log = LoggerFactory.getLogger(ChapterTwoLinkedList.class);

	/*
	 * In C++ are "static" classes. In Java, nested classes are not static by
	 * default (this non-static variety is also called an "inner class"), which
	 * means they require an instance of their outer class, which they track
	 * behind the scenes in a hidden field. Hence if we plan to instantiate the
	 * inner class from a static method we need to mark the class as static.
	 */

	private static <T> LinkedListNode convertToLinkedList(Iterable<T> queue) {
		return convertToLinkedListHelper(queue.iterator());
	}

	private static <T> LinkedListNode<T> convertToLinkedListHelper(Iterator<T> copyQueue) {
		T data = null;
		if (copyQueue.hasNext()) {
			data = copyQueue.next();
		} else {
			return null;
		}

		LinkedListNode node = new LinkedListNode<T>();
		node.data = data;
		node.next = convertToLinkedListHelper(copyQueue);

		return node;
	}

	private static <T> String toString(LinkedListNode<T> head) {
		LinkedListNode<T> node = head;
		StringBuilder builder = new StringBuilder();
		while (node != null) {
			builder.append("| ").append(node.data).append(" |");
			builder.append("->");
			node = node.next;
		}
		builder.append("| ").append("NULL").append(" |");
		return builder.toString();
	}

	public static <T> boolean compareNodes(LinkedListNode<T> n1, LinkedListNode<T> n2) {
		// Two nulls can also be equal
		if (n1 == n2) {
			return true;
		}
		// System.out.println(toString(n1));
		// System.out.println(toString(n2));
		return (n1 != null && n2 != null && n1.data.equals(n2.data)) && compareNodes(n1.next, n2.next);
	}

	public static void run() {

		System.out.println("\n****\nRunning Chapter 2 - Linked List\n****");
		question1();
		question2();
		question3();
		question4();
		question5();
		question6();
		question7();
		question8();

	}

	private static void question1() {
		System.out.println("Q1. Remove Duplicates from a Linked List - REDO inplace solution");
		Queue queue = new LinkedList<Integer>();
		queue.add(new Integer(1));
		queue.add(new Integer(1));
		queue.add(new Integer(2));
		queue.add(new Integer(3));
		queue.add(new Integer(2));
		queue.add(new Integer(3));
		queue.add(new Integer(4));
		queue.add(new Integer(5));
		queue.add(new Integer(5));
		LinkedListNode<Integer> head = convertToLinkedList(queue);

		queue = new LinkedList<Integer>();
		queue.add(new Integer(1));
		queue.add(new Integer(2));
		queue.add(new Integer(3));
		queue.add(new Integer(4));
		queue.add(new Integer(5));
		LinkedListNode<Integer> expectedHead = convertToLinkedList(queue);

		// Helper functions
		checkArgument(compareNodes(head, head));
		checkArgument(compareNodes(expectedHead, expectedHead));
		checkArgument(!compareNodes(expectedHead, head));
		checkArgument(!compareNodes(head, expectedHead));

		// new list
		checkArgument(compareNodes(solution1_1(head), expectedHead));
		checkArgument(compareNodes(solution1_1(expectedHead), expectedHead));

		// Same list modified
		checkArgument(compareNodes(solution1_2(head), head)); // head modified
		checkArgument(compareNodes(head, expectedHead));
		checkArgument(compareNodes(solution1_2(head), head));
		checkArgument(compareNodes(solution1_2(expectedHead), expectedHead));
		checkArgument(compareNodes(head, expectedHead));
	}

	private static <T> LinkedListNode solution1_1(LinkedListNode<T> head) {
		LinkedListNode<T> node = head;
		LinkedHashSet<T> hashSet = new LinkedHashSet<T>();
		while (node != null) {
			hashSet.add(node.data);
			node = node.next;
		}
		return convertToLinkedList(hashSet);
	}

	private static <T> LinkedListNode solution1_2(LinkedListNode<T> head) {
		LinkedListNode<T> node = head;
		LinkedListNode<T> nextNode = null;
		while (node != null) {
			nextNode = node;
			while (nextNode != null && nextNode.next != null) {
				if (nextNode.next.data.equals(node.data)) {
					nextNode.next = deleteNodeInPlace(nextNode.next);
				}
				nextNode = nextNode.next;
			}
			node = node.next;
		}
		return head;
	}

	private static <T> LinkedListNode<T> deleteNodeInPlace(LinkedListNode<T> nextNode) {
		if (nextNode == null || nextNode.next == null) {
			return null;
		}
		nextNode.data = (T) nextNode.next.data;
		nextNode.next = nextNode.next.next;
		return nextNode;
	}

	private static void question2() {
		System.out.println("Q2. Return kth to last element.");
		Queue list = new LinkedList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		list.add(6);
		list.add(7);
		list.add(8);
		LinkedListNode<Integer> head = convertToLinkedList(list);
		checkArgument(solution2_1(head, 0).equals(new LinkedListNode(8)));
		checkArgument(solution2_1(head, 2).equals(new LinkedListNode(6)));
		checkArgument(solution2_1(head, 9) == null);
		checkArgument(solution2_1(head, 3).equals(new LinkedListNode(5)));
		checkArgument(solution2_1(head, 1).equals(new LinkedListNode(7)));
	}

	private static <T> LinkedListNode<T> solution2_1(LinkedListNode<T> head, int i) {
		int count = 0;
		LinkedListNode<T> node = head;
		LinkedListNode<T> temp = null;
		while (node != null) {
			count++;
			if (count > i) {
				if (temp == null)
					temp = head;
				else
					temp = temp.next;
			}
			node = node.next;
		}
		return temp;
	}

	private static void question3() {
		System.out.println("Q3. Remove a node and return the number of nodes deleted (In place) - REDO");
		Queue queue = new LinkedList<Integer>();
		queue.add(new Integer(1));
		queue.add(new Integer(1));
		queue.add(new Integer(2));
		queue.add(new Integer(3));
		queue.add(new Integer(2));
		queue.add(new Integer(1));
		queue.add(new Integer(3));
		queue.add(new Integer(4));
		queue.add(new Integer(5));
		queue.add(new Integer(5));
		queue.add(new Integer(1));
		LinkedListNode<Integer> head = convertToLinkedList(queue);

		queue = new LinkedList<Integer>();
		queue.add(new Integer(2));
		queue.add(new Integer(3));
		queue.add(new Integer(2));
		queue.add(new Integer(3));
		queue.add(new Integer(4));
		queue.add(new Integer(5));
		queue.add(new Integer(5));
		LinkedListNode<Integer> expectedHead = convertToLinkedList(queue);

		// Using in place delete - hard
		checkArgument(compareNodes(solution3_1(head, new Integer(1)), expectedHead));
		checkArgument(compareNodes(solution3_1(expectedHead, new Integer(1)), expectedHead));
		checkArgument(compareNodes(solution3_1(expectedHead, new Integer(1)), expectedHead));
		checkArgument(compareNodes(solution3_1(new LinkedListNode(new Integer(3)), new Integer(3)), null));

		expectedHead = convertToLinkedList(queue);

		queue = new LinkedList<Integer>();
		queue.add(new Integer(1));
		queue.add(new Integer(1));
		queue.add(new Integer(2));
		queue.add(new Integer(3));
		queue.add(new Integer(2));
		queue.add(new Integer(1));
		queue.add(new Integer(3));
		queue.add(new Integer(4));
		queue.add(new Integer(5));
		queue.add(new Integer(5));
		queue.add(new Integer(1));
		head = convertToLinkedList(queue);

		// Easy solution
		checkArgument(compareNodes(solution3_2(head, new Integer(1)), expectedHead));
		checkArgument(compareNodes(solution3_2(expectedHead, new Integer(1)), expectedHead));
		checkArgument(compareNodes(solution3_2(expectedHead, new Integer(1)), expectedHead));
		checkArgument(compareNodes(solution3_2(new LinkedListNode(new Integer(3)), new Integer(3)), null));
	}

	private static <T> LinkedListNode<T> solution3_1(LinkedListNode head, T data) {
		if (head == null) {
			return null;
		}
		LinkedListNode node = head;
		while (node.next != null) {
			if (node.next.data.equals(data)) {
				node.next = deleteNodeInPlace(node.next);
			} else {
				node = node.next;
			}
		}
		if (head.data.equals(data)) {
			head = head.next;
		}
		return head;
	}

	private static <T> LinkedListNode<T> solution3_2(LinkedListNode head, T data) {
		LinkedListNode node = head;
		while (node != null) {
			if (node.next != null && node.next.data.equals(data)) {
				node.next = node.next.next;
			}
			node = node.next;
		}

		if (head != null && head.data.equals(data)) {
			head = head.next;
		}
		return head;
	}

	private static void question4() {
		System.out.println("Q4. Put a value in place (descending order)");
		Queue queue = new LinkedList<Integer>();
		queue.add(new Integer(1));
		queue.add(new Integer(1));
		queue.add(new Integer(2));
		queue.add(new Integer(3));
		queue.add(new Integer(2));
		queue.add(new Integer(1));
		queue.add(new Integer(3));
		queue.add(new Integer(4));
		queue.add(new Integer(5));
		queue.add(new Integer(5));
		queue.add(new Integer(1));
		LinkedListNode<Integer> head = convertToLinkedList(queue);

		queue = new LinkedList<Integer>();
		queue.add(new Integer(5));
		queue.add(new Integer(5));
		queue.add(new Integer(4));
		queue.add(new Integer(3));
		queue.add(new Integer(2));
		queue.add(new Integer(3));
		queue.add(new Integer(2));
		queue.add(new Integer(1));
		queue.add(new Integer(1));
		queue.add(new Integer(1));
		queue.add(new Integer(1));
		LinkedListNode<Integer> expectedHead = convertToLinkedList(queue);
		checkArgument(compareNodes(solution4_1(head, 1), expectedHead));

	}

	private static LinkedListNode<Integer> solution4_1(LinkedListNode<Integer> head, Integer integer) {
		if (head == null || head.next == null)
			return head;

		LinkedListNode<Integer> node = head;
		while (node.next != null) {
			if (((Integer) node.next.data).compareTo(integer) > 0) {
				LinkedListNode<Integer> temp = node.next.next;
				node.next.next = head;
				head = node.next;
				node.next = temp;
			} else {
				node = node.next;
			}
		}
		return head;
	}

	private static void question5() {
		System.out.println("Q5. Find the sum when numbers are arranged in a list in reverse order and normal order");
		Queue queue = new LinkedList<Integer>();
		queue.add(new Integer(1));
		queue.add(new Integer(1));
		queue.add(new Integer(8));
		queue.add(new Integer(9));
		LinkedListNode<Integer> head1 = convertToLinkedList(queue);

		queue = new LinkedList<Integer>();
		queue.add(new Integer(9));
		queue.add(new Integer(1));
		queue.add(new Integer(9));
		LinkedListNode<Integer> head2 = convertToLinkedList(queue);

		// Reverse order - easy (9811 + 919 = 10730)
		queue = new LinkedList<Integer>();
		queue.add(new Integer(0));
		queue.add(new Integer(3));
		queue.add(new Integer(7));
		queue.add(new Integer(0));
		queue.add(new Integer(1));
		LinkedListNode<Integer> result = convertToLinkedList(queue);
		checkArgument(compareNodes(solution5_1(head1, head2), result));
		// Correct order -hard (1189 + 919 = 2108 )
		queue = new LinkedList<Integer>();
		queue.add(new Integer(2));
		queue.add(new Integer(1));
		queue.add(new Integer(0));
		queue.add(new Integer(8));
		LinkedListNode<Integer> result2 = convertToLinkedList(queue);
		checkArgument(compareNodes(solution5_2(head1, head2), result2));
	}

	private static <T extends Integer> LinkedListNode<T> solution5_1(LinkedListNode<T> head1, LinkedListNode<T> head2) {
		T carry = (T) new Integer(0);
		LinkedListNode<T> temp = new LinkedListNode(0);
		LinkedListNode<T> head = temp;
		while (head1 != null || head2 != null) {
			if (head1 != null) {
				temp.data = (T) new Integer(temp.data + head1.data);
				head1 = head1.next;
			}
			if (head2 != null) {
				temp.data = (T) new Integer(temp.data + head2.data);
				head2 = head2.next;
			}
			carry = (T) new Integer(temp.data.intValue() / 10);
			temp.data = (T) new Integer(temp.data.intValue() % 10);
			temp.next = new LinkedListNode();
			temp = temp.next;
			temp.data = (T) new Integer(carry);
		}
		return head;
	}

	private static LinkedListNode<Integer> solution5_2(LinkedListNode<Integer> head1, LinkedListNode<Integer> head2) {
		int s1 = size(head1);
		int s2 = size(head2);
		LinkedListNode<Integer> soln = null;
		if (s1 > s2) // head1 is longer
			soln = solution5_2_helper(head1, head2, s1 - s2);
		else
			soln = solution5_2_helper(head2, head1, s2 - s1);

		// Trimming first 0
		if (soln != null && soln.data == 0) {
			soln = soln.next;
		}
		return soln;
	}

	// invariant = size(head1) >= size(head2)
	// Returns Carry -> Solution - Imp in case the length of final answer > the
	// largest input
	private static LinkedListNode<Integer> solution5_2_helper(LinkedListNode<Integer> head1,
			LinkedListNode<Integer> head2, int diff) {

		if (head1 == null && head2 == null)
			return null;

		LinkedListNode<Integer> node = null;
		LinkedListNode<Integer> nextNode = null;
		if (diff != 0) {
			node = new LinkedListNode<Integer>(head1.data);
			nextNode = solution5_2_helper(head1.next, head2, diff - 1);
		} else {
			node = new LinkedListNode<Integer>(head1.data + head2.data);
			nextNode = solution5_2_helper(head1.next, head2.next, 0);
		}

		if (nextNode != null) {
			node.data = node.data + nextNode.data;
			node.next = nextNode.next;
		}
		;

		LinkedListNode<Integer> carry = new LinkedListNode<Integer>(node.data / 10);
		node.data = node.data % 10;
		carry.next = node;
		return carry;
	}

	private static <T>int size(LinkedListNode<T> head) {
		if (head == null)
			return 0;
		return 1 + size(head.next);
	}

	private static void question6() {
		System.out.println("Q6. Check if the list is a palindrome");
		Queue queue = new LinkedList<Integer>();
		queue.add(new Integer(1));
		queue.add(new Integer(1));
		queue.add(new Integer(8));
		queue.add(new Integer(9));
		LinkedListNode<Integer> head = convertToLinkedList(queue);	
		checkArgument(!solution6_1(head));
		
		queue = new LinkedList<Integer>();
		queue.add(new Integer(1));
		queue.add(new Integer(8));
		queue.add(new Integer(8));
		queue.add(new Integer(1));
		head = convertToLinkedList(queue);
		checkArgument(solution6_1(head));
		
		queue = new LinkedList<Integer>();
		queue.add(new Integer(1));
		queue.add(new Integer(8));
		queue.add(new Integer(9));
		queue.add(new Integer(8));
		queue.add(new Integer(1));
		head = convertToLinkedList(queue);
		checkArgument(solution6_1(head));
		
		queue = new LinkedList<Integer>();
		queue.add(new Integer(1));
		queue.add(new Integer(8));
		queue.add(new Integer(7));
		queue.add(new Integer(9));
		queue.add(new Integer(8));
		queue.add(new Integer(1));
		head = convertToLinkedList(queue);
		checkArgument(!solution6_1(head));
	}

	private static <T>boolean solution6_1(LinkedListNode<T> head) {
		// min size = 2
		return solution6_1_helper(head, size(head)).result;
	}

	// mins size = 0 and min size of head = 2
	private static <T>Result<T> solution6_1_helper(LinkedListNode<T> head, int size) {
		if(size == 0) // even
			return new Result<T>(head, true);
		else if (size == 1) // odd
			return new Result<T>(head.next, true);
		Result<T> res = solution6_1_helper(head.next, size - 2);
		if(!res.result)
			return res;

		if(head.data.equals(res.node.data) && res.result)
			res.node = res.node.next;
		else
			res.result = false;

		return res;
	}

	private static void question7() {
		System.out.println("Q7. Find the point of intersection of two lists");
		Queue queue = new LinkedList<Integer>();
		queue.add(new Integer(1));
		queue.add(new Integer(1));
		queue.add(new Integer(8));
		queue.add(new Integer(9));
		LinkedListNode<Integer> head1 = convertToLinkedList(queue);

		queue = new LinkedList<Integer>();
		queue.add(new Integer(1));
		queue.add(new Integer(1));
		queue.add(new Integer(1));
		queue.add(new Integer(2));
		queue.add(new Integer(8));
		queue.add(new Integer(9));
		LinkedListNode<Integer> head2 = convertToLinkedList(queue);

		queue = new LinkedList<Integer>();
		queue.add(new Integer(8));
		queue.add(new Integer(9));
		LinkedListNode<Integer> result = convertToLinkedList(queue);

		// Recursive approach
		checkArgument(compareNodes(solution7_1(head1, head2), result));
	}

	// Using recursion
	private static LinkedListNode<Integer> solution7_1(LinkedListNode<Integer> head1, LinkedListNode<Integer> head2) {
		int s1 = size(head1);
		int s2 = size(head2);
		if (s1 > s2) // head1 is longer
			return solution7_1_helper(head1, head2, s1 - s2).node;
		else
			return solution7_1_helper(head2, head1, s2 - s1).node;
	}

	static class Result<T> {
		LinkedListNode<T> node;
		boolean result;

		public Result(LinkedListNode<T> n, boolean r) {
			node = n;
			result = r;
		}
	}

	// in variant size(head1) > size(head2)
	private static Result<Integer> solution7_1_helper(LinkedListNode<Integer> head1, LinkedListNode<Integer> head2,
			int diff) {
		if (head1 == null && head2 == null)
			return new Result<Integer>(null, true);

		Result<Integer> result = null;
		if (diff != 0) {
			result = solution7_1_helper(head1.next, head2, diff - 1);
		} else {
			result = solution7_1_helper(head1.next, head2.next, diff);
		}
		if (head1.data.equals(head2.data) && result.result) {
			return new Result<Integer>(head1, true);
		}
		result.result = false;
		return result;
	}

	private static void question8() {
		System.out.println("Q8. Check of the linked list is has a loop, if yes then where");
		LinkedListNode<Character> node1 = new LinkedListNode<Character>('a');
		LinkedListNode<Character> node2 = new LinkedListNode<Character>('b');
		LinkedListNode<Character> node3 = new LinkedListNode<Character>('c');
		LinkedListNode<Character> node4 = new LinkedListNode<Character>('d');
		LinkedListNode<Character> node5 = new LinkedListNode<Character>('e');
		LinkedListNode<Character> node6 = new LinkedListNode<Character>('f');
		LinkedListNode<Character> node7 = new LinkedListNode<Character>('g');
		
		node1.next = node2;
		node2.next = node3;
		node3.next = node4;
		node4.next = node5;
		node5.next = node6;
		node6.next = node7;
		
		LinkedListNode<Character> repeatitionPoint =node3;
		node7.next = repeatitionPoint;
		checkArgument(solution8_1(node1).data.equals(repeatitionPoint.data));
		
		repeatitionPoint =node5;
		node7.next = repeatitionPoint;
		checkArgument(solution8_1(node1).data.equals(repeatitionPoint.data));
		
		repeatitionPoint =node6;
		node7.next = repeatitionPoint;
		checkArgument(solution8_1(node1).data.equals(repeatitionPoint.data));
		
		repeatitionPoint =node7;
		node7.next = repeatitionPoint;
		checkArgument(solution8_1(node1).data.equals(repeatitionPoint.data));
	}

	private static LinkedListNode<Character> solution8_1(LinkedListNode<Character> node) {
		if(node == null)
			return null;
		LinkedListNode<Character> n1=node;
		LinkedListNode<Character> n2=node;
		
		do {
			n1=n1.next;
			if(n2.next == null) {
				break;
			}
			n2 = n2.next.next;
		} while(n1 != null && n2 != null && n1 != n2);
		
		if(n1 == n2) {
			n1 = node;
			do {
				n1=n1.next;
				n2 = n2.next;
			} while(n1 != n2);
			return n1;
		}
		return null;
	}

}

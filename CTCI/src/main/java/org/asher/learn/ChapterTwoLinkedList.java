package org.asher.learn;

import java.awt.List;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.traversal.NodeFilter;

import static com.google.common.base.Preconditions.checkArgument;

public class ChapterTwoLinkedList {
	private static final Logger log = LoggerFactory.getLogger(ChapterTwoLinkedList.class);
	
	/*
	 *  In C++ are "static" classes. In Java, nested classes are not static by default 
	 *  (this non-static variety is also called an "inner class"), which means they 
	 *  require an instance of their outer class, which they track behind the scenes 
	 *  in a hidden field. Hence if we plan to instantiate the inner class from a static method
	 *  we need to mark the class as static.
	 */
	public static class Node <T> {
		Node next = null;
		T data = null;
	}
	
	private static <T> Node convertToLinkedList(Iterable<T> queue) {
		return convertToLinkedListHelper(queue.iterator());
	}

	private static <T> Node<T> convertToLinkedListHelper(Iterator<T> copyQueue) {
		T data = null;
		if(copyQueue.hasNext()) {
			data = copyQueue.next();
		} else {
			return null;
		}
		
		Node node = new Node<T>();
		node.data = data;
		node.next = convertToLinkedListHelper(copyQueue);
		
		return node;
	}
	
	private static <T> String toString(Node<T> head) {
		Node<T> node = head; 
		StringBuilder builder = new StringBuilder();
		while(node != null) {
			builder.append("| ").append(node.data).append(" |");
			builder.append("->");
			node = node.next;
		}
		builder.append("| ").append("NULL").append(" |");
		return builder.toString();
	}
	
	public static <T>boolean compareNodes(Node<T> n1, Node<T> n2) {
		// Two nulls can also be equal
		if(n1 == n2 ) {
			return true;
		}
		// System.out.println(toString(n1));
		// System.out.println(toString(n2));
		return (n1 != null && n2 !=null && n1.data.equals(n2.data)) && compareNodes(n1.next, n2.next);
	}
	
	public static void run() {
		System.out.println("Running Chapter 2 - Linked List");
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
		System.out.println("Q1. Remove Duplicates from a Linked List");
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
		Node<Integer> head = convertToLinkedList(queue);
		
		queue = new LinkedList<Integer>();
		queue.add(new Integer(1));
		queue.add(new Integer(2));
		queue.add(new Integer(3));
		queue.add(new Integer(4));
		queue.add(new Integer(5));
		Node<Integer> expectedHead = convertToLinkedList(queue);
		
		// Helper functions
		checkArgument(compareNodes(head, head));
		checkArgument(compareNodes(expectedHead, expectedHead));
		checkArgument(! compareNodes(expectedHead, head));
		checkArgument(! compareNodes(head, expectedHead));
		
		// new list
		checkArgument(compareNodes(solution1_1(head), expectedHead));
		checkArgument(compareNodes(solution1_1(expectedHead), expectedHead));
		
		// Same list modified
		checkArgument(compareNodes(solution2_1(head), head)); // head modified
		checkArgument(compareNodes(head, expectedHead));
		checkArgument(compareNodes(solution2_1(head), head));
		checkArgument(compareNodes(solution2_1(expectedHead), expectedHead));	
		checkArgument(compareNodes(head, expectedHead));
	}
	
	private static <T> Node solution1_1(Node<T> head) {
		Node<T> node = head;
		LinkedHashSet<T> hashSet = new LinkedHashSet<T>();
		while(node != null) {
			hashSet.add(node.data);
			node = node.next;
		}
		return convertToLinkedList(hashSet);
	}
	
	private static <T> Node solution2_1(Node<T> head) {
		Node<T> node = head;
		Node<T> nextNode= nextNode = null; 
		while(node != null) {
			nextNode = node.next;
			while(nextNode != null) {
				if(nextNode.data.equals(node.data)) {
					nextNode = deleteNodeInPlace(nextNode);
				}
				nextNode = node.next;
			}
		}
		return head;
	}

	private static <T>Node<T> deleteNodeInPlace(Node<T> nextNode) {
		if(nextNode == null || nextNode.next == null) {
			return null;
		}
		nextNode.data = (T) nextNode.next.data;
		nextNode.next = nextNode.next.next;
		return nextNode;
	}

	private static void question2() {
		// TODO Auto-generated method stub
		
	}
	private static void question3() {
		// TODO Auto-generated method stub
		
	}
	private static void question4() {
		// TODO Auto-generated method stub
		
	}
	private static void question5() {
		// TODO Auto-generated method stub
		
	}
	private static void question6() {
		// TODO Auto-generated method stub
		
	}
	private static void question7() {
		// TODO Auto-generated method stub
		
	}
	private static void question8() {
		// TODO Auto-generated method stub
		
	}

}

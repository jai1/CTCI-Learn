package org.ctci.learn;

import static com.google.common.base.Preconditions.checkArgument;

import org.ctci.utils.CircularLinkedList;

public class ChapterSevenOOPsConcept {
	public static void run() {
		System.out.println("\n****\nRunning Chapter 7 - OOPs Concept\n****");
		question9();
	}

	private static void question9() {
		System.out.println("Q9. Create a circular linked list, also implement for(T data : list)");
		
		// Circular Linked list is used to implement round robin type algorithms 
		// It can be used in games where we can have turns one after the other (eg. Snake and ladders, chess)
		
		// http://stackoverflow.com/questions/975383/how-can-i-use-the-java-for-each-loop-with-custom-classes
		CircularLinkedList<Character> list = new CircularLinkedList<Character>();
		list.add('a').add('b').add('a');
		checkArgument(list.getNext() == 'a');
		
		checkArgument(list.peekNext() == 'b');
		
		checkArgument(list.toString().equals("|b| -> |a| -> |a| -> | NULL |"));
		
		checkArgument(list.size() == 3);
		
		checkArgument(! list.remove('c'));
		
		checkArgument(list.remove('b'));
		
		checkArgument(list.removeAll('a') == 2);
		
		checkArgument(list.size() == 0);
		try {
			list.getNext();
		} catch (Exception ex) {
			// Exception occured
			checkArgument(true);
		}
		
		list.add('a');
		checkArgument(list.peekNext() == 'a');
		checkArgument(list.getNext() == 'a');
		checkArgument(list.size() == 1);
		
		checkArgument(list.removeAll('a') == 1);
		checkArgument(list.size() == 0);
		
		list.addFirst('a');
		checkArgument(list.size() == 1);
		
		list.addFirst('b');
		list.addLast('z');
		checkArgument(list.size() == 3);
	}
}

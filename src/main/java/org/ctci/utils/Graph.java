package org.ctci.utils;

import java.util.LinkedList;
import java.util.List;

public class Graph<T> {
	public static enum GraphState {START, INPROGRESS, COMPLETE};
	public List<GraphNode<T>> nodes = new LinkedList<GraphNode<T>>();
	
	public boolean add(GraphNode<T> g) {
		return nodes.add(g);
	}
	
	public void reset() {
		for(GraphNode<T> node : nodes) {
			node.state = GraphState.START;
		}
	}
}

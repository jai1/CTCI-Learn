package org.asher.utils;

import java.util.LinkedList;
import java.util.List;

import org.asher.utils.Graph.GraphState;

public class GraphNode<T> {
	public T data;
	public List<GraphNode<T>> incomingNodes = new LinkedList<GraphNode<T>>();
	public List<Integer> weights = new LinkedList<Integer>();
	public GraphState state = GraphState.START;
	public GraphNode(T data) {
		this.data = data;
	}
	
	public boolean add(GraphNode<T> g) {
		return incomingNodes.add(g) || weights.add(0);
	}
	
}

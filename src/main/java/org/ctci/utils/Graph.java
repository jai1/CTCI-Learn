package org.ctci.utils;

import java.util.HashMap;


//TODO - this is not a general graph node - clean the nomenclature
public class Graph<T> {
	public static enum GraphState {START, INPROGRESS, COMPLETE};
	public HashMap<T, GraphNode<T>> nodes = new HashMap<T, GraphNode<T>>();
	
	public boolean addNode(GraphNode<T> g) {
		if (nodes.containsKey(g.nodeID)) {
			return false;
		}
		nodes.put(g.nodeID, g);
		return true;
	}
	
	public void reset() {
		for(T key : nodes.keySet()) {
			nodes.get(key).state = GraphState.START;
		}
	}
}

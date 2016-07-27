package org.ctci.utils;

import java.util.LinkedList;
import java.util.List;

import org.ctci.utils.Graph.GraphState;
import org.w3c.dom.Node;


public class GraphNode<T> {
	// Convert these to private
	public T nodeID;
	// list since there can be more than one edge connecting 2 nodes
	public List<GraphNode<T>> incomingNodes = new LinkedList<GraphNode<T>>();
	public List<Integer> incomingNodesWeights = new LinkedList<Integer>();
	
	public List<GraphNode<T>> outgoingNodes = new LinkedList<GraphNode<T>>();
	public List<Integer> outgoingNodesWeights = new LinkedList<Integer>();	
	
	private Integer nodeCost = 0;
	
	public GraphState state = GraphState.START;
	public GraphNode(T nodeID) {
		this.nodeID = nodeID;
	}
	
	public Integer getNodeCost() {
		return nodeCost;
	}
	
	public void setNodeCost(Integer cost) {
		nodeCost = cost;
	}
	
	public boolean addIncomingNodes(GraphNode<T> g) {
		return incomingNodes.add(g) || incomingNodesWeights.add(0);
	}
	
	public boolean addOutgoingNodes(GraphNode<T> g) {
		return outgoingNodes.add(g) || outgoingNodesWeights.add(0);
	}
}

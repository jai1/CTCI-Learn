package org.asher.utils;

public class BinaryTreeNode<T> {
	public BinaryTreeNode<T> left = null;
	public BinaryTreeNode<T> right = null;
	public T data = null;

	public BinaryTreeNode() {
	}

	public BinaryTreeNode(T temp) {
		data = temp;
	}

	public boolean equals(LinkedListNode<T> n) {
		return n != null && data.equals(n.data);
	}
	
	public String toString(){
		return data.toString();
	}

	public boolean deepEquals(BinaryTreeNode<T> node) {
		if(node == null) {
			return false;
		}
		
		if((this.left != null && node.left == null) 
				|| (this.left == null && node.left != null)
				|| (this.right !=null && node.right == null)
				|| (this.right ==null && node.right != null))
			return false;
		
		if(!this.data.equals(node.data))
			return false;

		if(this.left == null)
			return (this.right == null || this.right.deepEquals(node.right));
			
		if(this.right == null)
			return (this.left == null || this.left.deepEquals(node.left));	
		
		return (this.left.deepEquals(node.left)) && (this.right.deepEquals(node.right));
	}
}

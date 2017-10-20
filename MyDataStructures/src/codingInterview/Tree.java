package codingInterview;

public class Tree {
	
	public class TreeNode{
		public String name;
		public TreeNode left, right;
	}
	
	private TreeNode root;
	
	
	// In-order traversal:
	// Visit the left branch first, then the current node, then the right branch
	void inOrderTraversal(TreeNode node){
		if (node != null) {
			inOrderTraversal(node.left);
			visit(node);
			inOrderTraversal(node.right);
		}
	}
	
	// Pre-order traversal:
	// Visit the current node, then the left followed by the right branch
	void preOrderTraversal(TreeNode node){
		if (node != null){
			visit(node);
			preOrderTraversal(node.left);
			preOrderTraversal(node.right);
		}
	}
	
	String visit(TreeNode node){
		return node.name;
	}
	
}

class Node{
	String key;
	Node left,right;
	public Node(String item){
		key=item;
		left=right=null;
	}
}
class FamilyBinaryTree{
    Node root;
    // Inorder traversal (Left, Root, Right)
    void InorderTraverseTree(Node node) {
        if (node != null) {
            InorderTraverseTree(node.left);
            System.out.print(" " + node.key);
            InorderTraverseTree(node.right);
        }
    }
	// Preorder traversal (root ,left, right)
	void PreorderTraverseTree(Node node) {
		if(node!=null){
			System.out.print(" "+node.key);
			PreorderTraverseTree(node.left);
			PreorderTraverseTree(node.right);
		}
	}
	//Postorder traversal(left,right,root)
	void PostorderTraverseTree(Node node) {
        if (node != null) {
			PostorderTraverseTree(node.left);
			PostorderTraverseTree(node.right);
			System.out.print(" "+node.key);
		}
	}
    public static void main(String[] args) {
        FamilyBinaryTree tree = new FamilyBinaryTree();
        tree.root = new Node("C");
        tree.root.left = new Node("C");
        tree.root.right = new Node("S");
        tree.root.left.left = new Node("C");
		tree.root.left.right = new Node("M");
		tree.root.right.left = new Node("M");
		tree.root.right.right = new Node("S");
        System.out.print("Family Binary Tree(Inorder):");
        tree.InorderTraverseTree(tree.root);
		System.out.print("\nFamily Binary Tree(Preorder):");
        tree.PreorderTraverseTree(tree.root);
		System.out.print("\nFamily Binary Tree(Postorder):");
        tree.PostorderTraverseTree(tree.root);
    }
}
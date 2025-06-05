class BinarySearchTree{
    // Node class representing each node in the BST
    class Node {
        int key;
        Node left, right;

        public Node(int item) {
            key = item;
            left = right = null;
        }
    }

    Node root;

    // Constructor
    BinarySearchTree() {
        root = null;
    }

    // Insert a new key
    void insert(int key) {
        root = insertRec(root, key);
    }

    // Recursive insert function
    Node insertRec(Node root, int key) {
        if (root == null) {
            root = new Node(key);
            return root;
        }
        if (key < root.key)
            root.left = insertRec(root.left, key);
        else if (key > root.key)
            root.right = insertRec(root.right, key);
        return root;
    }

    // Inorder traversal (Left, Root, Right)
    void inorder(){
        inorderRec(root);
    }

    void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.print(root.key + " ");
            inorderRec(root.right);
        }
    }

    
    void preorder(){
        preorderrec(root);
    }
    void preorderrec(Node root) {
		if(root!=null){
			System.out.print(root.key+" ");
			preorderrec(root.left);
			preorderrec(root.right);
		}
    }


    void postorder(){
        postorderRec(root);
    }

    void postorderRec(Node root) {
        if (root != null) {
            postorderRec(root.left);
            
            postorderRec(root.right);
            System.out.print(root.key + " ");
        }
    }

    // Main method to test the BST
    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree();

        /* Insert nodes into the BST */
        tree.insert(50);
        tree.insert(30);
        tree.insert(20);
        tree.insert(40);
        tree.insert(70);
        tree.insert(60);
        tree.insert(80);

        /* Print inorder traversal of the BST */
        System.out.println("Inorder traversal:");
        tree.inorder();
        System.out.println("preorder traversal:");
        tree.preorder();
        System.out.println("postorder traversal:");
        tree.postorder();
        
    }
}


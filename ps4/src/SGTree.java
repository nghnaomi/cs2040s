/**
 * Scapegoat Tree class
 *
 * This class contains an implementation of a Scapegoat tree.
 */

public class SGTree {
    /**
     * TreeNode class.
     *
     * This class holds the data for a node in a binary tree.
     *
     * Note: we have made things public here to facilitate problem set grading/testing.
     * In general, making everything public like this is a bad idea!
     *
     */
    public static class TreeNode {
        int key;
        public TreeNode left = null;
        public TreeNode right = null;
        public TreeNode parent = null;
        public int weight = 1;

        TreeNode(int k) {
            key = k;
        }
    }

    // Root of the binary tree
    public TreeNode root = null;

    /**
     * Counts the number of nodes in the subtree rooted at node
     *
     * @param node the root of the subtree
     * @return number of nodes
     */
    public int countNodes(TreeNode node) {
        return node == null
            ? 0
            : 1 + countNodes(node.left) + countNodes(node.right);
    }

    /**
     * Builds an array of nodes in the subtree rooted at node
     *
     * @param node the root of the subtree
     * @return array of nodes
     */
    public TreeNode[] enumerateNodes(TreeNode node) {
        // TODO: Implement this LEFT MID RIGHT
        TreeNode[] inOrder = new TreeNode[countNodes(node)];
        if (node == null) {
            return inOrder;
        } else {
            int leftSide = countNodes(node.left);
            int rightSide = countNodes(node.right);
            TreeNode[] left = enumerateNodes(node.left);
            TreeNode[] right = enumerateNodes(node.right);
            for (int i = 0; i < leftSide; i++) {
                inOrder[i] = left[i];
            }
            inOrder[leftSide] = node;
            for (int j = 0; j < rightSide; j++) {
                inOrder[leftSide + j + 1] = right[j];
            }
            return inOrder;
        }
    }

    /**
     * Builds a tree from the list of nodes
     * Returns the node that is the new root of the subtree
     *
     * @param nodeList ordered array of nodes
     * @return the new root node
     */
    public TreeNode buildTree(TreeNode[] nodeList) {
        // TODO: Implement this
        if (nodeList.length < 1) {
            return null;
        } else {
            return buildTreeHelper(nodeList, 0, nodeList.length - 1, null);
        }
    }

    public TreeNode buildTreeHelper(TreeNode[] nodeList, int left, int right, TreeNode parent)
    {
        if (left > right) {
            return null;
        } else {
            int mid = (left + right) / 2;
            TreeNode newTree = nodeList[mid];
            newTree.parent = parent;
            newTree.left = buildTreeHelper(nodeList, left, mid - 1, newTree);
            newTree.right = buildTreeHelper(nodeList, mid + 1, right, newTree);

            newTree.weight = 1 + (newTree.left != null ? newTree.left.weight : 0) +
                    (newTree.right != null ? newTree.right.weight : 0);

            return newTree;
        }
    }
    /**
     * Determines if a node is balanced. If the node is balanced, this should return true. Otherwise, it should return
     * false. A node is unbalanced if either of its children has weight greater than 2/3 of its weight.
     *
     * @param node a node to check balance on
     * @return true if the node is balanced, false otherwise
     */
    public boolean checkBalance(TreeNode node) {
        // TODO: Implement this
        if (node == null || (node.left == null && node.right == null)) {
            return true;
        }

        int leftWeight = (node.left == null) ? 0 : node.left.weight;
        int rightWeight = (node.right == null) ? 0 : node.right.weight;
        double maxWeight = (2.0 / 3.0) * node.weight;


        if ((leftWeight > maxWeight && node.left != null) || (rightWeight > maxWeight && node.right != null)) {
            return false;
        }

        return true;
    }

    /**
    * Rebuilds the subtree rooted at node
    * 
    * @param node the root of the subtree to rebuild
    */
    public void rebuild(TreeNode node) {
        // Error checking: cannot rebuild null tree
        if (node == null) {
            return;
        }

        TreeNode p = node.parent;
        TreeNode[] nodeList = enumerateNodes(node);
        TreeNode newRoot = buildTree(nodeList);

        if (p == null) {
            root = newRoot;
        } else if (node == p.left) {
            p.left = newRoot;
        } else {
            p.right = newRoot;
        }

        newRoot.parent = p;
    }

    /**
    * Inserts a key into the tree
    *
    * @param key the key to insert
    */
    public void insert(int key) {
        if (root == null) {
            root = new TreeNode(key);
            return;
        }

        insert(key, root);
    }

    // Helper method to insert a key into the tree
    private void insert(int key, TreeNode node) {
        node.weight++;

        if (key <= node.key) {
            if (node.left == null) {
                node.left = new TreeNode(key);
                node.left.parent = node;
            } else {
                insert(key, node.left);
            }
        } else {
            if (node.right == null) {
                node.right = new TreeNode(key);
                node.right.parent = node;
            } else {
                insert(key, node.right);
            }
        }

        TreeNode unbalanced = null;
        TreeNode curr = node;

        while (curr != null) {
            if (!checkBalance(curr)) {
                unbalanced = curr;
            }
                curr = curr.parent;
        }

        if (unbalanced != null) {
            rebuild(unbalanced);
        }
    }

    // Simple main function for debugging purposes
    public static void main(String[] args) {
        SGTree tree = new SGTree();
        for (int i = 0; i < 100; i++) {
            tree.insert(i);
        }
        tree.rebuild(tree.root);
    }
}

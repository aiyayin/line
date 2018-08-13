package line.tree;

/**
 * Created by ying.fu on 2018/7/16.
 */

public class Tree {
    private TreeNode mRoot;

    public TreeNode getRoot() {
        return mRoot;
    }

    public void setRoot(TreeNode root) {
        mRoot = root;
    }

    public int getLevel() {
        if (mRoot == null)
            return 0;
        else
            return getLevel(1, mRoot);
    }

    public boolean isLeaf(TreeNode node) {
        if (node != null && node.getLeftNode() == null && node.getRightNode() == null)
            return true;
        return false;
    }

    public int getLevel(int level, TreeNode node) {
        if (isLeaf(node)) {
            return level;
        } else {
            level = level + 1;
            level = Math.max(getLevel(level, node.getLeftNode()), getLevel(level, node.getRightNode()));
            return level;
        }
    }

    String s = "";

    public String preTraversal() {
        s = "";
        if (mRoot == null)
            return s;
        else
            s = preTraversal(mRoot);
        return s;
    }


    private String preTraversal(TreeNode node) {
        s = s + node.getValue() + ", ";
        if (!isLeaf(node)) {
            preTraversal(node.getLeftNode());
            preTraversal(node.getRightNode());
        }
        return s;
    }

    public String inTraversal() {
        s = "";
        if (mRoot == null)
            return s;
        else {
            s = inTraversal(mRoot);
        }
        return s;
    }

    private String inTraversal(TreeNode node) {
        if (!isLeaf(node)) {
            inTraversal(node.getLeftNode());
            s = s + node.getValue() + ", ";
            inTraversal(node.getRightNode());
        } else {
            s = s + node.getValue() + ", ";
        }
        return s;
    }

    public String postTraversal() {
        s = "";
        if (mRoot == null)
            return s;
        else
            s = postTraversal(mRoot);
        return s;
    }

    private String postTraversal( TreeNode node) {
        if (!isLeaf(node)) {
            postTraversal(node.getLeftNode());
            postTraversal(node.getRightNode());
            s = s + node.getValue() + ", ";
        } else {
            s = s + node.getValue() + ", ";
        }
        return s;
    }


}

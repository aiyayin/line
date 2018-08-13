package line.tree;

/**
 *
 * Created by ying.fu on 2018/7/16.
 */

public class TreeNode {
    private String value;
    private TreeNode leftNode;
    private TreeNode rightNode;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TreeNode getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(TreeNode leftNode) {
        this.leftNode = leftNode;
    }

    public TreeNode getRightNode() {
        return rightNode;
    }

    public void setRightNode(TreeNode rightNode) {
        this.rightNode = rightNode;
    }

    public TreeNode(String leftValue, TreeNode leftNode, TreeNode rightNode) {
        this.value = leftValue;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }
}

package cz.cvut.fel.pjv;

/**
 * Created by Martin on 09.04.2017.
 */
public class NodeImpl implements Node {

    int value;
    NodeImpl left;
    NodeImpl right;

    public NodeImpl(int value, NodeImpl left, NodeImpl right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }
    public NodeImpl(int value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }


    public void setRight(NodeImpl right) {
        this.right = right;
    }

    public void setLeft(NodeImpl left) {
        this.left = left;
    }

    public void setValue(int value) {
        this.value = value;
    }

    /**
     * @return left child of this node, or null
     */
    @Override
    public NodeImpl getLeft() {
        return this.left;
    }

    /**
     * @return right child of this node, or null
     */
    @Override
    public NodeImpl getRight() {
        return this.right;
    }

    /**
     * @return value of this node
     */
    @Override
    public int getValue() {
        return this.value;
    }



}

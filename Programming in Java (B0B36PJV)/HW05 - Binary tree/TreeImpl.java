package cz.cvut.fel.pjv;

/**
 * Created by Martin on 09.04.2017.
 */
public class TreeImpl implements Tree {

    private Node root;
    String tree;
    int depth;

    public TreeImpl() { // Default constructor without parameter
        this.root = null;
        this.tree = "";
        this.depth = 0;
    }

    private void setRoot(Node root){
        this.root = root;
    }

    @Override
    public void setTree(int[] values) {
        this.tree = "";
        this.root = null;
        this.depth = 0;

        if (values.length > 0) {
            NodeImpl firstNode = new NodeImpl(values[(values.length / 2)]);
            setTreeRecursive(values, this.depth, firstNode);
        }
    }

    private void setTreeRecursive(int[] values, int depth, NodeImpl actualNode) {

        for(int i = 0; i < depth; i++) {
            this.tree = this.tree + " ";
        }
        this.tree = this.tree + "- " + Integer.toString(actualNode.getValue()) + "\n";

        if (values.length > 2) {
            // nodes for left and right
            int[] leftValues = new int[values.length / 2];
            for (int i = 0; i < leftValues.length; i++) {
                leftValues[i] = values[i];
            }
            int[] rightValues = new int[values.length - ((values.length / 2) + 1)];
            for (int i = 0; i < rightValues.length; i++) {
                rightValues[i] = values[(values.length / 2) + 1 + i];
            }

            NodeImpl leftNode = new NodeImpl(leftValues[(leftValues.length / 2)]);
            NodeImpl rightNode = new NodeImpl(rightValues[(rightValues.length / 2)]);

            actualNode.setLeft(leftNode);
            actualNode.setRight(rightNode);

            if (this.root == null) { setRoot(actualNode); }

            setTreeRecursive(leftValues, depth + 1, actualNode.getLeft());
            setTreeRecursive(rightValues, depth + 1, actualNode.getRight());


        }
        else if (values.length == 2){
            // nodes only left
            int[] leftValues = new int[values.length / 2];
            for (int i = 0; i < leftValues.length; i++) {
                leftValues[i] = values[i];
            }

            NodeImpl leftNode = new NodeImpl(leftValues[(leftValues.length / 2)]);

            actualNode.setLeft(leftNode);

            if (this.root == null) { setRoot(actualNode); }


            for(int i = 0; i < depth + 1; i++) {
                this.tree = this.tree + " ";
            }
            this.tree = this.tree + "- " + Integer.toString(actualNode.getLeft().getValue()) + "\n";
        }
        else { // no more nodes - children are nulls
            if (this.root == null) { setRoot(actualNode); }
        }
    }

    @Override
    public Node getRoot() {
        return this.root;
    }

    @Override
    public String toString() {
        return this.tree;
    }
}
package alg;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree {

    public Node root;
    List<Node> tree = new ArrayList<>();

    public BinaryTree(int value) {
        root = new Node(null, value);
        tree.add(root);
    }

    public void addNode (Node root, int value) {
        if(value < root.value){
            if(root.leftChild != null) {
                addNode(root.leftChild, value);
            }
            else {
                Node node = new Node(root, value);
                root.leftChild = node;
                tree.add(node);
                //root.leftChild = new Node(root, value);
                //tree.add(root.leftChild);
            }
        }
        else{
            if(root.rightChild != null) {
                addNode(root.rightChild, value);
            }
            else {
                Node node = new Node(root, value);
                root.rightChild = node;
                tree.add(node);
                //root.rightChild = new Node(root, value);
                //tree.add(root.rightChild);

            }
        }
    }
}

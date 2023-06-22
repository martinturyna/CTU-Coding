package alg;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        FastReader fr = new FastReader();
        int N = fr.nextInt();

        BinaryTree binaryTree = new BinaryTree(fr.nextInt());

        for (int i = 0; i < N-1; i++) {
            binaryTree.addNode(binaryTree.root, fr.nextInt());
        }

        /*for (Node n: binaryTree.tree) {
            int l = -1, r = -1;
            if (n.leftChild != null) {
                l = n.leftChild.value;
            }
            if (n.rightChild != null) {
                r = n.rightChild.value;
            }
            System.out.println("Node: " + n.value + " Left: " + l + " Right: " + r);

        }*/

        Logic logic = new Logic(binaryTree, fr.nextInt(), fr.nextInt());
        logic.solution();


    }
}

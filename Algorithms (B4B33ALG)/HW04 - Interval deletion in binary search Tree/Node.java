package alg;

public class Node implements Comparable<Node> {

    public Node parent;
    public Node leftChild = null;
    public Node rightChild = null;
    public boolean isDeleted = false;
    public int value;

    public Node(Node parent, int value) {
        this.value = value;
        this.parent = parent;
    }

    @Override
    public int compareTo(Node o) { return this.value-o.value; }
}

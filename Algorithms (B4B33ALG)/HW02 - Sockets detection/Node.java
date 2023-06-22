package alg;

import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable<Node>{
    private int value;
    private List<Node> neighbours = new ArrayList<Node>();

    public Node(int value) {
        this.value = value;
    }

    public void addLink(Node node) {
        neighbours.add(node);
    }

    public int getNumberOfLinks() {
        return neighbours.size();
    }

    public Node getLink(int index) {
        // Check link size
        return neighbours.get(index);
    }

    public List<Node> getNeighbours() {
        return neighbours;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int compareTo(Node o) {
        return this.value-o.getValue();
    }
}

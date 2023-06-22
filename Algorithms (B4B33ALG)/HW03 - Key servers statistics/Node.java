package alg;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private int value;
    private List<Edge> edges = new ArrayList<Edge>();
    private boolean isKeyNode = false;
    private boolean deleted = false;
    public boolean isOrigin = false;
    public int virtualEdges;


    public Node(int value) {
        this.value = value;
    }

    public void addEdge(Node node, int cost) {
        edges.add(new Edge(node, cost));
    }

    public int getNumberOfEdges() {
        return edges.size();
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }


    public Edge getEdge(int index) { return edges.get(index); }

    public List<Edge> getEdges() {
        return edges;
    }

    public int getValue() {
        return value;
    }

    public void setIsKeyNode(boolean b) {
        isKeyNode = b;
    }

    public boolean isKeyNode() {
        return isKeyNode;
    }

}

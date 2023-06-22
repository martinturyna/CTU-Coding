package alg;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    List<Node> graph = new ArrayList<Node>();

    private int size;
    private int keySize;

    public Graph(int size, int keySize) {
        this.size = size;
        this.keySize = keySize;
        for(int i = 0; i < size; i++) {
            graph.add(new Node(i));
        }
    }

    public void createConnection(int node1, int node2, int cost) {
        graph.get(node1).addEdge(graph.get(node2), cost);
        graph.get(node2).addEdge(graph.get(node1), cost);
    }

    public Node getNode(int node) {
        return graph.get(node);
    }


    public int getKeySize() { return keySize; }
    public int getSize() {
        return size;
    }


}

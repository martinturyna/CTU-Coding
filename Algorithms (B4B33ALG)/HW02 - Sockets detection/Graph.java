package alg;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    List<Node> graph = new ArrayList<Node>();
    List<Node> possibleSockets = new ArrayList<Node>();

    private int size; // Number of nodes in graph
    private int connections;

    public Graph(int size, int connections) {
        this.size = size;
        this.connections = connections;
        ///// [1]
        for(int i = 0; i < size; i++) {
            graph.add(new Node(i+1));
        }
    }

    public void createConnection(int node1, int node2) {
        /*if (graph.get(node1-1).getNumberOfLinks() == 1) { possibleSockets.add(graph.get(node1-1)); }
        if (graph.get(node2-1).getNumberOfLinks() == 1) { possibleSockets.add(graph.get(node2-1)); }
        ///// [2]
        if (graph.get(node1-1).getNumberOfLinks() == 2) { possibleSockets.remove(graph.get(node1-1)); }
        if (graph.get(node2-1).getNumberOfLinks() == 2) { possibleSockets.remove(graph.get(node2-1)); }*/

        graph.get(node1-1).addLink(graph.get(node2-1));
        graph.get(node2-1).addLink(graph.get(node1-1));
    }

    public Node getNode(int node) {
        return graph.get(node);
    }

    public int getSize() {
        return size;
    }

    public int getConnections() {
        return connections;
    }

}

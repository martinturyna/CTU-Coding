package student;

import cz.cvut.atg.zui.astar.Utils;
import eu.superhub.wp5.planner.planningstructure.GraphEdge;
import eu.superhub.wp5.planner.planningstructure.GraphNode;

/**
 * Created by Martin on 24.03.2018.
 */
public class Node {

    public GraphNode currentGraphNode;
    public GraphNode previousGraphNode;
    public GraphEdge edgeToCurrentGraphNode;
    public Node previousNode;

    private double timeAsAPrice; // for Dijkstra
    private double astarPrice; // timeAsAPrice + heuristic

    public Node(GraphNode current) {
        this.currentGraphNode = current;
        this.timeAsAPrice = 0.0;
    }

    public Node(GraphNode previous, GraphNode current, GraphEdge edge, Node previousNode, GraphNode destination) {
        this.previousGraphNode = previous;
        this.currentGraphNode = current;
        this.edgeToCurrentGraphNode = edge;
        this.previousNode = previousNode;

        // When you expand a new node, price to that node has to be updated from previous path
        this.timeAsAPrice = previousNode.timeAsAPrice +
                ((edge.getLengthInMetres() / 1000) / edge.getAllowedMaxSpeedInKmph());
        // f(x) = g(x) + h(x)
        this.astarPrice = this.timeAsAPrice + (Utils.distanceInKM(current, destination) / 120);

    }

    public void update(Node n) {
        this.previousGraphNode = n.previousGraphNode;
        this.currentGraphNode = n.currentGraphNode;
        this.edgeToCurrentGraphNode = n.edgeToCurrentGraphNode;
        this.previousNode = n.previousNode;
        this.timeAsAPrice = n.timeAsAPrice;
        this.astarPrice = n.astarPrice;
    }

    public long getNodeId() {
        return currentGraphNode.getId();
    }

    public GraphNode getCurrentGraphNode() {
        return this.currentGraphNode;
    }

    public double getTimeAsAPrice() {
        return this.timeAsAPrice;
    }

    public double getAstarPrice() {
        return this.astarPrice;
    }

}

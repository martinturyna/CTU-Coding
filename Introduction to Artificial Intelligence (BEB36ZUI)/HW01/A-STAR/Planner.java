package student;

import cz.cvut.atg.zui.astar.AbstractOpenList;
import cz.cvut.atg.zui.astar.PlannerInterface;
import cz.cvut.atg.zui.astar.RoadGraph;
import eu.superhub.wp5.planner.planningstructure.GraphEdge;
import eu.superhub.wp5.planner.planningstructure.GraphNode;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Planner implements PlannerInterface {
    private OpenList openList = new OpenList();

    @Override
    public List<GraphEdge> plan(RoadGraph graph, GraphNode origin, GraphNode destination) {

        HashMap<Long, Double> visitedNodes = new HashMap<>();
        Node startNode = new Node(origin);
        Node tobeExpandedNode;
        openList.add(startNode);
        visitedNodes.put(startNode.getNodeId(), startNode.getTimeAsAPrice());

        while(!openList.priorityQueue.isEmpty()) {
            tobeExpandedNode = openList.priorityQueue.poll();

            if (tobeExpandedNode.getNodeId() == destination.getId()) {
                Stack<GraphEdge> tmp = new Stack<>();
                List<GraphEdge> finalPath = new ArrayList<>();
                Node n = tobeExpandedNode;

                // Gotta way from end to start - stack do the job to swap
                while(n.previousNode != null) {
                    if (n.edgeToCurrentGraphNode == null) {
                        System.out.println("Something bad happened");
                    }
                    tmp.push(n.edgeToCurrentGraphNode);
                    n = n.previousNode;
                }

                while(!tmp.isEmpty()) {
                    finalPath.add(tmp.pop());
                }
                return finalPath;
            }
            if (graph.getNodeOutcomingEdges(tobeExpandedNode.getNodeId()) != null) {
                for (GraphEdge e : graph.getNodeOutcomingEdges(tobeExpandedNode.getNodeId())) {

                    Node n = new Node (tobeExpandedNode.getCurrentGraphNode(), graph.getNodeByNodeId(e.getToNodeId()),
                                       e, tobeExpandedNode, destination);

                    if (visitedNodes.containsKey(n.getNodeId())) {
                        if (visitedNodes.get(n.getNodeId()) > n.getTimeAsAPrice()) {
                            openList.update(n);
                            visitedNodes.put(n.getNodeId(), n.getTimeAsAPrice());
                        }
                    } else {
                        openList.add(n);
                        visitedNodes.put(n.getNodeId(), n.getTimeAsAPrice());
                    }
                }
            }
        }
        return null;
    }
    @Override
    public AbstractOpenList getOpenList() {
        return openList;
    }
}
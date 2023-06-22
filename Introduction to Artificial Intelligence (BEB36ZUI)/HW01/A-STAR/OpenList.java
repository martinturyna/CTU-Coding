package student;

import cz.cvut.atg.zui.astar.AbstractOpenList;
import eu.superhub.wp5.planner.planningstructure.GraphNode;

import java.util.PriorityQueue;

/**
 * Created by Martin on 24.03.2018.
 */
public class OpenList extends AbstractOpenList<Node> {
    public PriorityQueue<Node> priorityQueue = new PriorityQueue<>(new PriceComparator());

    public void update(Node n) {
        for (Node n1 :  priorityQueue) {
            if (n1.getNodeId() == n.getNodeId()) {
                n1.update(n);
                Node help = priorityQueue.poll();
                priorityQueue.add(help);
                return;
            }
        }
        this.add(n);
    }

    @Override
    protected boolean addItem(Node item) {
        return priorityQueue.add(item);
    }
}

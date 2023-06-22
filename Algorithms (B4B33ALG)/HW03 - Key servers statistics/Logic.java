package alg;

import java.util.Stack;

public class Logic {
    Graph graph;
    int keyNodesNumber;

    public Logic(Graph graph) {
        this.graph = graph;
        this.keyNodesNumber = graph.getKeySize();
    }


    public void solution() {
        Stack<Node> stack = new Stack<Node>();
        int cost = 0;
        int redundantCost = 0;
        for (Node n: graph.graph) {
            n.virtualEdges = n.getNumberOfEdges();
            if (n.getNumberOfEdges() == 1) {
                stack.push(n);
            }
        }

        while(!stack.isEmpty()) {

            Node currentNode = stack.pop();
            Node nextNode = new Node(-1);
            int tmp = 0;

            if (currentNode.isOrigin == true) {
                redundantCost = cost;
            }

            for (Edge e: currentNode.getEdges()) {
                if (!e.isDeleted()) {
                    nextNode = e.getTo();
                    break;
                }
                tmp++;
            }

            if (currentNode.isKeyNode()) {
                cost += 2 * (currentNode.getEdge(tmp).getCost());
                nextNode.setIsKeyNode(true);
            }

            currentNode.setDeleted(true);
            nextNode.virtualEdges--;

            if (nextNode.virtualEdges == 1) {
                stack.push(nextNode);
            }
        }

        int found = 0;
        Node currentNode = new Node(-1);

        for (int i = 0; i < graph.getSize(); i++) {
            if (graph.getNode(i).isDeleted()) {
                continue;
            }
            if (graph.getNode(i).isKeyNode()) {
                currentNode = graph.getNode(i);
                found++;
            }
        }

        if (found == 1) {
            System.out.println(redundantCost);
        } else {


            int maxCost = 0;
            int cycleCost = 0;
            int tmpCost = 0;

            Node start = currentNode;
            int round = 0;
            while(true) {
                round++;
                if (currentNode == start && round > 1) {
                    if (tmpCost > maxCost) {
                        maxCost = tmpCost;
                    }
                    break;
                }

                if (currentNode.isKeyNode() && (tmpCost > maxCost)) {
                    maxCost = tmpCost;
                    tmpCost = 0;
                } else if (currentNode.isKeyNode()){
                    tmpCost = 0;
                }

                for(int i = 0; i < currentNode.getNumberOfEdges(); i++) {

                    if (round > 2 && (currentNode.getEdge(i).to == start)) {
                        cycleCost += currentNode.getEdge(i).getCost();
                        tmpCost += currentNode.getEdge(i).getCost();
                        currentNode.setDeleted(true);
                        currentNode = currentNode.getEdge(i).to;
                        break;
                    }

                    if (!currentNode.getEdge(i).to.isDeleted()) {
                        cycleCost += currentNode.getEdge(i).getCost();
                        tmpCost += currentNode.getEdge(i).getCost();
                        currentNode.setDeleted(true);
                        currentNode = currentNode.getEdge(i).to;
                        break;
                    }
                }
            }

            int n = 2 * (cycleCost - maxCost);
            if (n < cycleCost) {
                System.out.println(2*(cycleCost-maxCost)+cost);
            } else {
                System.out.println(cycleCost+cost);
            }
        }
    }
}

package alg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Logic {
    Graph graph;
    List<Integer> sockets = new ArrayList<Integer>();

    public Logic(Graph graph) {
        this.graph = graph;
        for (Node n: graph.graph) {
            if (n.getNumberOfLinks() == 2) {
                graph.possibleSockets.add(n);
            }
        }
        Collections.sort(graph.possibleSockets);
    }

    public boolean isSolution(Node node) { // The main logic of the whole project
        Node leftChild = node.getLink(0);
        Node rightChild = node.getLink(1);

        if (leftChild.getNumberOfLinks() != rightChild.getNumberOfLinks()) { return false; }
        //sockets.add(node.getValue());

        List<Node> leftQueue = new ArrayList<Node>();
        List<Node> rightQueue = new ArrayList<Node>();
        leftQueue.add(leftChild);
        rightQueue.add(rightChild);

        boolean[] visited = new boolean[graph.getSize() + 1];
        visited[node.getValue()] = true;

        while(!leftQueue.isEmpty() && !rightQueue.isEmpty()) {
            int[] frequency = new int[graph.getSize() + 1];
            int asymmetry = 0;

            Collections.sort(leftQueue);
            Collections.sort(rightQueue);
            int l = 0;
            int r = 0;

            int leftSize = leftQueue.size();
            int rightSize = rightQueue.size();

            while(l < leftSize || r < rightSize) {

                if (l < leftSize && r < rightSize) {
                    if ((leftQueue.get(l).getValue() == rightQueue.get(r).getValue())
                            && (leftQueue.get(l).getNumberOfLinks() == 2)) {
                        sockets.add(node.getValue());
                        if ((leftSize == 1) && (rightSize == 1)) {
                            sockets.add(leftQueue.get(l).getValue());
                        }
                        //sockets.add(leftQueue.get(l).getValue());
                        return true;
                        /*visited[leftQueue.get(l).getValue()] = true;
                        l++;
                        r++;
                        continue;*/
                    }
                }

                if (l < leftSize) {
                    if (!visited[leftQueue.get(l).getValue()]) {
                        for (Node n : leftQueue.get(l).getNeighbours()) {
                            if (!visited[n.getValue()]) {
                                frequency[n.getNumberOfLinks()]++;
                                if (frequency[n.getNumberOfLinks()] == 1) {
                                    asymmetry++;
                                }
                                leftQueue.add(n);
                            }
                        }
                    }
                }

                if (r < rightSize) {
                    if (!visited[rightQueue.get(r).getValue()]) {
                        for (Node n : rightQueue.get(r).getNeighbours()) {
                            if (!visited[n.getValue()]) {
                                frequency[n.getNumberOfLinks()]--;
                                if (frequency[n.getNumberOfLinks()] == 0) {
                                    asymmetry--;
                                }
                                rightQueue.add(n);
                            }
                        }
                    }
                }

                if (l < leftSize) {
                    visited[leftQueue.get(l).getValue()] = true;
                }
                if (r < rightSize) {
                    visited[rightQueue.get(r).getValue()] = true;
                }

                if ((l < leftSize && (leftQueue.get(l).getValue() < rightQueue.get(r).getValue())) || (r >= rightSize)) {
                    l++;
                }
                else if ((r < rightSize && (leftQueue.get(l).getValue() > rightQueue.get(r).getValue())) || (l >= leftSize)) {
                    r++;
                }
            }

            if (asymmetry != 0) {
                //sockets.clear();
                return false;
            }

            for (int k = 0; k < leftSize; k++) {
                leftQueue.remove(0);
            }

            for (int k = 0; k < rightSize; k++) {
                rightQueue.remove(0);
            }
        }

        if ( leftQueue.isEmpty() && rightQueue.isEmpty() ) {
            return true;
        }
        else {
            //sockets.clear();
            return false;
        }
    }

    public void findSolution() {
        /*for (int i = 0; i < graph.getSize(); i++) {
            if (graph.getNode(i).getNumberOfLinks() == 2) {
                if (isSolution(graph.getNode(i))) {
                    if (!sockets.isEmpty()) {
                        int socketsSize;
                        if (sockets.size() > 100) {
                            socketsSize = 100;
                        }
                        else {
                            socketsSize = sockets.size();
                        }
                        Collections.sort(sockets);
                        System.out.print(sockets.get(0));
                        for (int j = 1; j < socketsSize; j++) {
                            System.out.print(" " + sockets.get(j));
                        }
                        System.out.println();
                        break;
                    }
                }
            }
        }*/

        ///// [3]
        Collections.sort(graph.possibleSockets);
        if (graph.getSize() == graph.getConnections()) {
            isSolution(graph.possibleSockets.get(0));
            System.out.println(sockets.get(0) + " " + sockets.get(1));
        } else {
            int counter = 0;
            for (Node n: graph.possibleSockets) {
                if (isSolution(n)) {
                    counter++;
                }
                if (counter == 100) {
                    break;
                }
            }
            System.out.print(sockets.get(0));
            for(int i = 1; i < sockets.size(); i++) {
                System.out.print(" " + sockets.get(i));
            }
            System.out.println();
        }




    }



}

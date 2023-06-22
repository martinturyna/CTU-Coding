package alg;

public class Edge {
    private int cost;
    Node to;
    private boolean status = true;

    public Edge(Node to, int cost) {
        this.to = to;
        this.cost = cost;
    }

    public Node getTo() {
        return to;
    }

    public int getCost() {
        return cost;
    }

    public boolean isDeleted() {
        return to.isDeleted();
    }



}

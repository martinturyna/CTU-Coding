package student;

import java.util.Comparator;

/**
 * Created by Martin on 25.03.2018.
 */
public class PriceComparator implements Comparator<Node> {

    @Override
    public int compare(Node o1, Node o2) {
        if (o1.getAstarPrice() < o2.getAstarPrice()) {
            return -1;
        }
        else if (o1.getAstarPrice() > o2.getAstarPrice()) {
            return 1;
        }
        else {
            return 0;
        }
    }
}

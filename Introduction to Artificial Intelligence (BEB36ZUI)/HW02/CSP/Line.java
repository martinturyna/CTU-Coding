package student;

import java.util.ArrayList;

/**
 * Created by Martin on 08.04.2018.
 */
public class Line {

    ArrayList<char[]> domain;
    ArrayList<ArrayList<Character>> result;
    ArrayList<Block> blocks;
    boolean assigned = false;
    boolean row = false;
    int position = -1;

    public Line(ArrayList<Block> b) {
        this.blocks = b;
        this.domain = new ArrayList<>();
    }
}

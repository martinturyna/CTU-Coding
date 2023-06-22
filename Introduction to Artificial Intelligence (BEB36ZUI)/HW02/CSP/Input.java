package student;

import java.util.ArrayList;

/**
 * Created by Martin on 07.04.2018.
 */
public class Input {

    private ArrayList<ArrayList<Block>> rows = new ArrayList<>();
    private ArrayList<ArrayList<Block>> columns = new ArrayList<>();


    public void read() {

        FastReader fr = new FastReader();

        String firstLine[] = fr.nextLine().split(",");

        int rowCount = Integer.parseInt(firstLine[0]);
        int columnCount = Integer.parseInt(firstLine[1]);
        int sum = rowCount + columnCount;

        for (int i = 0; i < sum; i++) {
            ArrayList<Block> wholeLine = new ArrayList<>();

            String line[] = fr.nextLine().split(",");

            for (int j = 0; j < line.length; j = j + 2) {
                Block b = new Block(Integer.parseInt(line[j+1]), line[j].charAt(0));
                wholeLine.add(b);
            }

            if (i < rowCount) {
                rows.add(wholeLine);
            }
            else {
                columns.add(wholeLine);
            }
        }
    }


    public ArrayList<ArrayList<Block>> getRows() {
        return rows;
    }

    public ArrayList<ArrayList<Block>> getColumns() {
        return columns;
    }

}

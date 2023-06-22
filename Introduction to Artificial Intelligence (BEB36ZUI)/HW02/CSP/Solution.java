package student;

import java.util.ArrayList;

/**
 * Created by Martin on 07.04.2018.
 */
public class Solution {

    private ArrayList<ArrayList<Block>> rows;
    private ArrayList<ArrayList<Block>> columns;

    private ArrayList<Line> rowLines;
    private ArrayList<Line> columnLines;
    private ArrayList<Line[]> worklist;

    private ArrayList<char[][]> results;

    private int rowsSize;
    private int columnsSize;

    long startTime;

    public Solution(ArrayList<ArrayList<Block>> rows, ArrayList<ArrayList<Block>> columns) {
        this.rows = rows;
        this.columns = columns;
        this.rowsSize = rows.size();
        this.columnsSize = columns.size();
        this.rowLines = new ArrayList<>();
        this.columnLines = new ArrayList<>();
        this.worklist = new ArrayList<>();
        this.results = new ArrayList<>();
    }

    public void solveTheNonogram() {
        createDomains();
        createWorklist();
        ac_3();

        char[][] result = new char[rowsSize][columnsSize];
        for (int r = 0; r < rowsSize; r++) {
            for (int c = 0; c < columnsSize; c++) {
                result[r][c] = ' ';
            }
        }

        startTime = System.currentTimeMillis();
        backtracking(0, result);

        if (results.isEmpty()) {
            System.out.println("null");
        } else {
            for (char[][] res : results) {
                for (int r = 0; r < rowsSize; r++) {
                    for (int c = 0; c < columnsSize; c++) {
                        System.out.print(res[r][c]);
                    }
                    System.out.println();
                }
                System.out.println();
            }
        }

    }

    public void backtracking(int depth, char[][] partialResult) {

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        if (elapsedTime > 420000) {
            //System.out.println(elapsedTime);
            return;
        }


        if (depth == rowsSize*columnsSize) {
            results.add(partialResult);
            return;
        }

        int addedFields = 0;
        int min = Integer.MAX_VALUE;
        int pos = 0;
        boolean row = false;

        for (Line r : rowLines) {
            if (r.domain.size() < min && !r.assigned) {
                min = r.domain.size();
                row = true;
                pos = r.position;
            }
        }

        for (Line c : columnLines) {
            if (c.domain.size() < min && !c.assigned) {
                min = c.domain.size();
                row = false;
                pos = c.position;
            }
        }

        if (row) {

            Line l = rowLines.get(pos);
            l.assigned = true;

            for (char[] d : l.domain) {

                char[][] copy = new char[rowsSize][columnsSize];

                for (int r = 0; r < rowsSize; r++) {
                    for (int c = 0; c < columnsSize; c++) {
                        copy[r][c] = partialResult[r][c];
                    }
                }

                boolean goNext = true;
                for (int i = 0; i < columnsSize; i++) {
                    if (copy[pos][i] != ' ') {
                        if (copy[pos][i] != d[i]) {


                            goNext = false;
                        }
                    }
                    else {
                        addedFields++;
                        copy[pos][i] = d[i];
                    }
                }
                if (goNext) {
                    backtracking(depth + addedFields, copy);
                }
                addedFields = 0;
            }
            l.assigned = false;
        }
        else {

            Line l = columnLines.get(pos);
            l.assigned = true;

            for (char[] d : l.domain) {

                char[][] copy = new char[rowsSize][columnsSize];

                for (int r = 0; r < rowsSize; r++) {
                    for (int c = 0; c < columnsSize; c++) {
                        copy[r][c] = partialResult[r][c];
                    }
                }

                boolean goNext = true;
                for (int i = 0; i < rowsSize; i++) {
                    if (copy[i][pos] != ' ') {
                        if (copy[i][pos] != d[i]) {
                            goNext = false;
                        }
                    }
                    else {
                        copy[i][pos] = d[i];
                        addedFields++;
                    }
                }
                if (goNext) {
                    backtracking(depth + addedFields, copy);
                }
                addedFields = 0;
            }
            l.assigned = false;
        }
    }

    public void createWorklist() {
        for (Line row: rowLines) {
            for (Line column : columnLines) {
                Line[] pair = new Line[2];
                pair[0] = row;
                pair[1] = column;
                worklist.add(pair);
            }
        }
    }

    public void ac_3() {
        while (!worklist.isEmpty()) {
            Line[] pair = worklist.remove(0);
            if (arc_reduce(pair[0], pair[1])) {
                if (pair[0].domain.isEmpty()) {
                    System.out.println("Failure");
                } else {
                    ArrayList<Line> lines;
                    if (pair[0].row) {
                        lines = columnLines;
                    } else {
                        lines = rowLines;
                    }

                    for (Line z: lines) {
                        if (z != pair[1]) {
                            Line[] newPair = new Line[2];
                            newPair[0] = z;
                            newPair[1] = pair[0];
                            worklist.add(newPair);
                        }
                    }
                }
            }
        }
    }

    public boolean arc_reduce(Line x, Line y) {
        boolean change = false;
        boolean found = false;
        ArrayList<char[]> toRemove = new ArrayList<>();
        for (char[] vx : x.domain) {
            for (char[] vy : y.domain) {
                if (vx[y.position] == vy[x.position]) {
                    found = true;
                }
            }
            if (!found) {
                toRemove.add(vx);
                change = true;
            }
            found = false;
        }
        x.domain.removeAll(toRemove);
        return change;
    }

    public void createDomains() {
        int position = 0;
        for (ArrayList<Block> row : rows) {
            Line l = new Line(row);
            l.row = true;
            l.position = position;
            position++;
            createDomainForLine(l, new char[columnsSize], 0, 0, '_');
            rowLines.add(l);
        }
        position = 0;
        for (ArrayList<Block> column: columns) {
            Line l = new Line(column);
            l.position = position;
            position++;
            createDomainForLine(l, new char[rowsSize], 0, 0, '_');
            columnLines.add(l);
        }
    }

    public void createDomainForLine(Line l, char[] c, int depth, int blockIdx, char previousChar) {

        int end = columnsSize;
        if (!l.row) {
            end = rowsSize;
        }

        if (depth == end) {
            if (blockIdx >= l.blocks.size()) {
                l.domain.add(c);
            }
        } else {

            if (previousChar == '_') {
                if (blockIdx < l.blocks.size()) {
                    if (l.blocks.get(blockIdx).getSize() + depth <= end) {
                        char[] cNew = c.clone();
                        int blockSize = l.blocks.get(blockIdx).getSize();
                        char blockCol = l.blocks.get(blockIdx).getColour();
                        for (int i = depth; i < depth+blockSize; i++) {
                            cNew[i] = blockCol;
                        }
                        createDomainForLine(l, cNew, depth+blockSize, blockIdx + 1, blockCol);

                    }
                }

                char[] cNew2 = c.clone();
                cNew2[depth] = '_';
                createDomainForLine(l, cNew2, depth+1, blockIdx, '_');

            } else {
                if (blockIdx < l.blocks.size()) {
                    if (l.blocks.get(blockIdx).getSize() + depth <= end) {
                        if (l.blocks.get(blockIdx).getColour() != previousChar) {
                            char[] cNew2 = c.clone();
                            char blockCol = l.blocks.get(blockIdx).getColour();
                            int blockSize = l.blocks.get(blockIdx).getSize();
                            for (int i = depth; i < depth+blockSize; i++) {
                                cNew2[i] = blockCol;
                            }
                            createDomainForLine(l, cNew2, depth+blockSize, blockIdx + 1, blockCol);
                        }
                    }
                }

                char[] cNew = c.clone();
                cNew[depth] = '_';
                createDomainForLine(l, cNew, depth+1, blockIdx, '_');
            }
        }
    }
}

package student;

import java.util.ArrayList;

/**
 * Created by Martin on 07.04.2018.
 *
 * In my program I use AC3 technique of CSP.
 * As variables I chose rows and columns. Domains are all admissible combinations of blocks, that are defined
 * in input. Constrain for a single variable is position of blocks.
 * Constrain for relation between X and Y is defined by in common field, that must be of the same value
 * as column and row at the [x][y] position.
 *
 * First of whole i create all domains for every single row and column (solution.createDomain).
 * Next step is to run AC3 algortihm, that (usually) reduce domains (solution.ac_3)).
 * Last step is classic backtracking (solution.backtracking), that combines all rows and columns and based on rules
 * delete the wrong combinations.
 *
 * Note: In my backtracking is somewhere small bug. When we have a clear result right from AC3, everything is OK (krtek,
 * nebo csp_example), but
 * in case of combinations between rows and columns it doesnt make any result.
 *
 * + MCV (Most constraint value): I use this technique in backtracking function, where i always choose the variable
 * (row or column) with the smallest number of domains. It should make my program faster.
 *
 */


public class CSPMain {

    public static void main(String[] args)  {
        Input input = new Input();
        input.read();
        Solution solution = new Solution(input.getRows(), input.getColumns());
        solution.solveTheNonogram();
    }
}

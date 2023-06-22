package alg;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        FastReader fr = new FastReader();
        int x = fr.nextInt();
        int y = fr.nextInt();
        Solution solution = new Solution(x, y);

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                solution.map[i][j] = new Sector(fr.nextInt());
            }
        }

        solution.robotCount = fr.nextInt();

        for (int i = 0; i < solution.robotCount; i++) {
            solution.addRobot(new Robot(fr.nextInt(), fr.nextInt(), fr.nextInt(), fr.nextInt()));
        }
        solution.doIt();
    }
}

package alg;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Solution {

    public Sector map[][];
    public int xSize;
    public int ySize;
    public int robotCount;
    public ArrayList<Robot> robots;
    public int maxValueReached = 0;
    public int gstopX;
    public int gstopY;
    int count = 0;

    public Solution(int x, int y) {
        this.xSize = x;
        this.ySize = y;
        this.map = new Sector[x][y];
        this.robots = new ArrayList<Robot>();
    }

    public void addRobot(Robot r) {
        robots.add(r);
    }

    public void doIt() {
        Robot arr[] = new Robot[robotCount];
        int a = 0;

        for (Robot r: robots) {
            arr[a] = r;
            a++;
        }
        recursion(0, this.robotCount, arr);
        System.out.println(maxValueReached);
    }

    public void backRobotBack(Robot r, int stopX, int stopY) {
        int finalX = r.startX - r.finishX;
        int finalY = r.startY - r.finishY;
        if (finalX == 0) {
            if (finalY > 0) {
                for (int i = r.startY; i >= stopY; i--) {
                    map[r.startX][i].visited = false;
                }
            }
            else {
                for (int i = r.startY; i <= stopY; i++) {
                    map[r.startX][i].visited = false;
                }
            }
        }
        else {
            if (finalX > 0) {
                for (int i = r.startX; i >= stopX; i--) {
                    map[i][r.startY].visited = false;
                }
            }
            else {
                for (int i = r.startX; i <= stopX; i++) {
                    map[i][r.startY].visited = false;
                }
            }
        }
    }

    public int goRobotGo(Robot r) {
        int finalX = r.startX - r.finishX;
        int finalY = r.startY - r.finishY;
        int value = 0;
        if (finalX == 0) {
            if (finalY > 0) {
                for (int i = r.startY; i >= r.finishY; i--) {
                    if (!map[r.startX][i].visited) {
                        value += map[r.startX][i].value;
                        map[r.startX][i].visited = true;
                    }
                    else {
                        gstopX = r.startX;
                        gstopY = i+1;
                        return value;
                    }
                }
            }
            else {
                for (int i = r.startY; i <= r.finishY; i++) {
                    if (!map[r.startX][i].visited) {
                        value += map[r.startX][i].value;
                        map[r.startX][i].visited = true;
                    }
                    else {
                        gstopX = r.startX;
                        gstopY = i-1;
                        return value;
                    }
                }
            }
        }
        else {
            if (finalX > 0) {
                for (int i = r.startX; i >= r.finishX; i--) {
                    if (!map[i][r.startY].visited) {
                        value += map[i][r.startY].value;
                        map[i][r.startY].visited = true;
                    }
                    else {
                        gstopX = i+1;
                        gstopY = r.startY;
                        return value;
                    }
                }
            }
            else {
                for (int i = r.startX; i <= r.finishX; i++) {
                    if (!map[i][r.startY].visited) {
                        value += map[i][r.startY].value;
                        map[i][r.startY].visited = true;
                    }
                    else {
                        gstopX = i-1;
                        gstopY = r.startY;
                        return value;
                    }
                }
            }

        }
        gstopX = r.finishX;
        gstopY = r.finishY;
        return value;
    }

    public void recursion(int value, int robotsLeft, Robot arr[]) {
        robotsLeft--;
        if (robotsLeft == 0) {
            for (int i = 0; i < robotCount; i++) {
                if (arr[i] != null) {
                    Robot r;
                    r = arr[i];
                    int tmp = value;
                    tmp += goRobotGo(r);
                    int tmpX = gstopX;
                    int tmpY = gstopY;
                    backRobotBack(r, tmpX, tmpY);
                    if (tmp > maxValueReached) {
                        maxValueReached = tmp;
                    }
                }

            }
        } else {
            for (int i = 0; i < robotCount; i++) {
                if (arr[i] != null) {
                    Robot r = arr[i];
                    arr[i] = null;
                    int tmp = value + goRobotGo(r);
                    int tmpX = gstopX;
                    int tmpY = gstopY;
                    recursion(tmp, robotsLeft, arr);
                    arr[i] = r;
                    backRobotBack(r, tmpX, tmpY);
                }
            }
        }
    }

}

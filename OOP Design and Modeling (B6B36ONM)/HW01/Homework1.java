/**
 * Created by Martin on 04.10.2017.
 */
public class Homework1 {
    private int hCall = 0;
    private static int iCall = 0;

    public boolean f() {
        return true;
    }

    public static boolean g() {
        return false;
    }

    public int h() {
        hCall++;
        return hCall;
    }

    public int i() {
        iCall++;
        return iCall;
    }
}

package student;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainWrapper {
    public static void main(String[] args) throws IOException {
        FileInputStream is = new FileInputStream(new File("input.txt"));
        System.setIn(is);
        CSPMain.main(args);
    }
}
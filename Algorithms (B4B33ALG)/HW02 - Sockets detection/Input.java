package alg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Input {

    int number1 = -1;
    int number2 = -1;
    BufferedReader reader;

    public Input() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public boolean readLine() throws IOException {
        String[] integersInString = reader.readLine().split(" ");
        if (integersInString[0] == null) {
            return false;
        }
        number1 = Integer.parseInt(integersInString[0]);
        number2 = Integer.parseInt(integersInString[1]);
        return true;
    }

    public int getNumber1() {
        return number1;
    }

    public int getNumber2() {
        return number2;
    }

}

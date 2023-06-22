package alg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Input {

    private int number1 = -1;
    private int number2 = -1;
    private int number3 = -1;
    private BufferedReader reader;
    private List<Integer> list = new ArrayList<Integer>();

    public Input() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public boolean readLine() throws IOException {
        String[] integersInString = reader.readLine().split(" ");
        if (integersInString[0] == null) {
            return false;
        }
        if (integersInString.length == 2) {
            number1 = Integer.parseInt(integersInString[0]);
            number2 = Integer.parseInt(integersInString[1]);
        } else if (integersInString.length == 3) {
            number1 = Integer.parseInt(integersInString[0]);
            number2 = Integer.parseInt(integersInString[1]);
            number3 = Integer.parseInt(integersInString[2]);
        }
        else {
            for (String s : integersInString ) {
                list.add(Integer.parseInt(s));
            }
        }


        return true;
    }
    public List<Integer> getList() {
        return list;
    }
    public int getNumber1() {
        return number1;
    }

    public int getNumber2() {
        return number2;
    }

    public int getNumber3() { return number3; }

}

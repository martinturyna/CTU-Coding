package alg;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Input input = new Input();
        input.readLine();

        Graph graph = new Graph(input.getNumber1(), input.getNumber2());

        for (int i = 0; i < graph.getConnections(); i++) {
            if (input.readLine()) {
                graph.createConnection(input.getNumber1(), input.getNumber2());
            }
            else {
                System.out.println("alg.Input Stream error");
                System.exit(0);
            }
        }


        Logic logic = new Logic(graph);
        logic.findSolution();

    }
}

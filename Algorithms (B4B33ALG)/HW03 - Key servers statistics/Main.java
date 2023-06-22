package alg;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        //long start_time = System.nanoTime();

        /*Input input = new Input();
        input.readLine();
        Graph graph = new Graph(input.getNumber1(), input.getNumber2());*/
        FastReader fr = new FastReader();
        Graph graph = new Graph(fr.nextInt(), fr.nextInt());

        /*for (int i = 0; i < graph.getSize(); i++) {
            input.readLine();
            graph.createConnection(input.getNumber1(), input.getNumber2(), input.getNumber3());
        }*/

        for (int i = 0; i < graph.getSize(); i++) {
            graph.createConnection(fr.nextInt(), fr.nextInt(), fr.nextInt());
        }

        /*
        input.readLine();
        for (Integer i: input.getList()) {
            graph.getNode(i).setIsKeyNode(true);
        }*/

        for (int i = 0; i < graph.getKeySize(); i++) {
            int tmp = fr.nextInt();
            graph.getNode(tmp).setIsKeyNode(true);
            graph.getNode(tmp).isOrigin = true;
        }


        //long time = System.nanoTime();
        //System.out.println((time - start_time) / 1e6);

        Logic logic = new Logic(graph);
        logic.solution();

        //long end_time = System.nanoTime();
        //System.out.println((end_time - start_time) / 1e6);

    }
}

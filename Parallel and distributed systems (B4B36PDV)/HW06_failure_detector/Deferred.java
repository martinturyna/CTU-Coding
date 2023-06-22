package cz.cvut.fel.agents.pdv.swim;

import cz.cvut.fel.agents.pdv.dsand.Message;

/**
 * Created by Martin on 10.05.2018.
 */
public class Deferred extends Message {
    private String s;

    public Deferred(String s) { this.s = s; }
    public String getS() {
        return s;
    }
}

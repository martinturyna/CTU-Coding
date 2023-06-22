package cz.cvut.fel.agents.pdv.swim;

import cz.cvut.fel.agents.pdv.dsand.Message;

/**
 * Created by Martin on 10.05.2018.
 */
public class DeferredAck extends Message{
    private String s;

    public DeferredAck(String s) { this.s = s; }
    public String getS() {
        return s;
    }

}

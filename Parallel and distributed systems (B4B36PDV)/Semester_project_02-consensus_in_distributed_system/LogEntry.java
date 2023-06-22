package cz.cvut.fel.agents.pdv.student;

import cz.cvut.fel.agents.pdv.dsand.Pair;

import java.io.Serializable;

public class LogEntry implements Serializable{

    public String requestId;
    public int term;
    public int index;
    public boolean committed;
    public Pair<String, String> data;
    public String msgSender;
    public int votes;
    public boolean Append;


    public LogEntry(String requestId, int term, int index, Pair<String, String> data, String msgSender) {
        this.Append = true;
        this.requestId = requestId;
        this.term = term;
        this.index = index;
        this.data = data;
        this.msgSender = msgSender;
        this.votes = 0;
        this.committed = false;
    }







}
package cz.cvut.fel.agents.pdv.student;

import cz.cvut.fel.agents.pdv.dsand.Message;

public class AppendEntries extends Message {

    public AppendEntriesStatus status;
    public int term;
    public LogEntry newLogEntry;
    public LogEntry prevLogEntry;
    public int numOfCommitted;
    public boolean success;
    //public boolean deleted;
    /*public Map<String, String> replicaDB;
    public List<LogEntry> replicaLog;*/

    public enum AppendEntriesStatus {
        REQUEST, REPLY
    }

    public AppendEntries(AppendEntriesStatus s, int term, int numOfCommitted, LogEntry prevLogEntry, LogEntry newLogEntry) {
        this.status = s;
        this.term = term;
        this.newLogEntry = newLogEntry;
        this.prevLogEntry = prevLogEntry;
        this.numOfCommitted = numOfCommitted;
        this.success = false;
        //this.deleted = false;
        /*this.replicaDB = new HashMap<>();
        this.replicaLog = new LinkedList<>();*/
    }

    public AppendEntriesStatus getStatus() { return status; }

    public void setStatus(AppendEntriesStatus as) { this.status = as; }

    public int getTerm() {
        return term;
    }


    public void setTerm(int term) { this.term = term; }

}
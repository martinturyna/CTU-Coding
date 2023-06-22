package cz.cvut.fel.agents.pdv.student;

import cz.cvut.fel.agents.pdv.dsand.Message;

public class RequestVote extends Message {

    public enum RequestVoteStatus {
        REQUEST, ACKNOWLEDGEMENT
    }

    private RequestVoteStatus status;
    private boolean voteGranted;
    private String leaderId;
    //private String candidateId;
    private int term;
    private int logSize;
    public int lastLogTerm;
    public int lastLogIndex;

    public RequestVote(int term, int logSize) {
        this.status = RequestVoteStatus.REQUEST;
        this.voteGranted = false;
        this.term = term;
        this.leaderId = null;
        this.logSize = logSize;

    }

    public int getLogSize() { return this.logSize; }

    public boolean isVoteGranted() { return voteGranted; }

    public int getTerm() {
        return term;
    }

    public void setVoteGranted(boolean voteGranted) { this.voteGranted = voteGranted; }

    public void setTerm(int term) { this.term = term; }

    public void setStatus(RequestVoteStatus s) { this.status = s; }

    public RequestVoteStatus getStatus() { return status; }

    public String getLeaderId() { return this.leaderId; }

    public void setLeaderId(String leaderId) { this.leaderId = leaderId; }
}
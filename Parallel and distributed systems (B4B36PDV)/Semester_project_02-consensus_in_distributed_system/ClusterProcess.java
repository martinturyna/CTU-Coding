package cz.cvut.fel.agents.pdv.student;

import cz.cvut.fel.agents.pdv.dsand.Message;
import cz.cvut.fel.agents.pdv.dsand.Pair;
import cz.cvut.fel.agents.pdv.raft.RaftProcess;
import cz.cvut.fel.agents.pdv.raft.messages.*;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * Vasim ukolem bude naimplementovat (pravdepodobne nejenom) tuto tridu. Procesy v clusteru pracuji
 * s logy, kde kazdy zanam ma podobu mapy - kazdy zaznam v logu by mel reprezentovat stav
 * distribuovane databaze v danem okamziku.
 *
 * Vasi implementaci budeme testovat v ruznych scenarich (viz evaluation.RaftRun a oficialni
 * zadani). Nasim cilem je, abyste zvladli implementovat jednoduchou distribuovanou key/value
 * databazi s garancemi podle RAFT.
 */
public class ClusterProcess extends RaftProcess<Map<String, String>> {

    private enum ServerStatus {
        FOLLOWER, CANDIDATE, LEADER
    }

    // ostatni procesy v clusteru
    private final List<String> otherProcessesInCluster;
    // maximalni spozdeni v siti
    private final int networkDelays;
    private Random rn = new Random();
    private ServerStatus serverStatus;
    private int serverTimer;
    private int numOfSupporters;
    private int heartBeatTimer;
    private int repeatLogTimer;
    private String leaderId;
    private int currentTerm;
    private Map<String, String> actual_db;
    private List<LogEntry> actual_log;
    private String votedFor;
    private int numOfCommitted;
    private ArrayList<Pair<String, Integer>> nextIndexes;

    public ClusterProcess(String id, Queue<Message> inbox, BiConsumer<String, Message> outbox,
                          List<String> otherProcessesInCluster, int networkDelays) {
        super(id, inbox, outbox);
        this.otherProcessesInCluster = otherProcessesInCluster;
        this.networkDelays = networkDelays;
        this.serverStatus = ServerStatus.FOLLOWER;
        this.serverTimer = 5 + rn.nextInt(15);
        this.heartBeatTimer = 4 - this.networkDelays;
        this.repeatLogTimer = 4 - this.networkDelays;
        this.numOfSupporters = 0;
        this.leaderId = null;
        this.currentTerm = 0;
        this.actual_log = new LinkedList<>();
        this.actual_db = new HashMap<>();
        this.votedFor = null;
        this.numOfCommitted = 0;
    }

    public int getLogSize() {
        return this.actual_log.size();
    }

    public int getLastLogIndex() { return this.actual_log.size() - 1; }

    @Override
    public Optional<Map<String, String>> getLastSnapshotOfLog() {
        if (actual_db.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(new HashMap<>(this.actual_db));
        }
    }

    @Override
    public String getCurrentLeader() {
        return leaderId;
    }


    @Override
    public void act() {

        if (!(this.serverStatus == ServerStatus.LEADER)) {
            this.serverTimer--;
        } else {
            this.heartBeatTimer--;
            this.repeatLogTimer--;
        }

        while(!super.inbox.isEmpty()) {

            Message msg = super.inbox.poll();

            if (msg instanceof RequestVote) {
                if (((RequestVote) msg).getStatus() == RequestVote.RequestVoteStatus.REQUEST) {
                    // If request from higher term, then save term and step down if LEADER or CANDIDATE
                    if (((RequestVote) msg).getTerm() > this.currentTerm) {
                        this.currentTerm = ((RequestVote) msg).getTerm();
                        this.votedFor = null;
                        this.serverStatus = ServerStatus.FOLLOWER;
                    }

                    if (((RequestVote) msg).getTerm() == this.currentTerm && (votedFor == null || votedFor.equals(msg.sender))
                            && this.getLogSize() >= ((RequestVote) msg).getLogSize()) {
                        this.votedFor = msg.sender;
                        this.serverTimer = 5 + rn.nextInt(15);
                        ((RequestVote) msg).setStatus(RequestVote.RequestVoteStatus.ACKNOWLEDGEMENT);
                        ((RequestVote) msg).setVoteGranted(true);
                        super.send(msg.sender, msg);
                    } else {
                        ((RequestVote) msg).setTerm(this.currentTerm);
                        super.send(msg.sender, msg);
                    }
                } else {// RequestVote.REPLY
                    if (((RequestVote) msg).getTerm() > this.currentTerm) {
                        this.currentTerm = ((RequestVote) msg).getTerm();
                        this.votedFor = null;
                        this.serverStatus = ServerStatus.FOLLOWER;
                        this.serverTimer = 5 + rn.nextInt(15);
                    } else if (((RequestVote) msg).isVoteGranted() && this.serverStatus == ServerStatus.CANDIDATE) {
                        // CANDIDATE - VoteGranted()
                        this.numOfSupporters++;
                        // CANDIDATE - Enough Votes! -> Become LEADER
                        if (this.numOfSupporters > ((this.otherProcessesInCluster.size() / 2) + 1)) {
                            this.serverStatus = ServerStatus.LEADER;
                            // Set up to null cause of first immediate heartbeat this tic
                            this.heartBeatTimer = 0;
                            this.repeatLogTimer = 4 - this.networkDelays;
                            this.leaderId = super.getId();

                            // Initialize  nextIndex for each to lastLogIndex + 1
                            this.nextIndexes = new ArrayList<>();
                            for (String s : this.otherProcessesInCluster) {
                                nextIndexes.add(new Pair<>(s, this.getLastLogIndex()+1));
                            }
                        }
                    }
                }
            } else if (msg instanceof AppendEntries) {
                AppendEntries appendEntries = (AppendEntries) msg;

                if (appendEntries.getStatus() == AppendEntries.AppendEntriesStatus.REQUEST) {
                    // 1. Return if term < currTerm
                    if (this.currentTerm > appendEntries.getTerm()) {
                        appendEntries.setTerm(this.currentTerm);
                        appendEntries.setStatus(AppendEntries.AppendEntriesStatus.REPLY);
                        super.send(msg.sender, appendEntries);
                        continue;
                    }
                    // 2. If term > currentTerm, currentTerm <- term
                    if (this.currentTerm < appendEntries.getTerm()) {
                        this.currentTerm = appendEntries.getTerm();
                        this.votedFor = null;
                    }
                    // 3. If CANDIDATE or LEADER, step down
                    this.serverStatus = ServerStatus.FOLLOWER;
                    // 4. Reset election timeout
                    this.serverTimer = 5 + rn.nextInt(15);
                    // If heartbeat => update leader
                    if (appendEntries.newLogEntry == null) {
                        this.leaderId = appendEntries.sender;
                    }
                    // 5. Return failure if log doesnt contain
                    // an entry at prevLogIndex whose term matches prevLogTerm
                    if (appendEntries.prevLogEntry != null) {
                        if (this.getLastLogIndex() >= appendEntries.prevLogEntry.index) {
                            if (!(appendEntries.prevLogEntry.term == this.actual_log.get(appendEntries.prevLogEntry.index).term)) {
                                // 6. If existing entries conflict with new entries, delete
                                // all existing entries starting with first existing entry
                                for (int i = this.getLastLogIndex(); i >= appendEntries.prevLogEntry.index; i--) {
                                    this.actual_log.remove(i);
                                }

                                appendEntries.success = false;
                                super.send(appendEntries.sender, appendEntries);
                                continue;
                            }
                        } else {
                            appendEntries.success = false;
                            super.send(appendEntries.sender, appendEntries);
                            continue;
                        }
                    }


                    if (appendEntries.newLogEntry != null) {
                        boolean notAdded = true;
                        for (LogEntry le : this.actual_log) {
                            if (le.requestId.equals(appendEntries.newLogEntry.requestId)) {
                                notAdded = false;
                                break;
                            }
                        }
                        if (notAdded) {
                            if (actual_log.size() > this.numOfCommitted) {
                                LogEntry tmpLE = this.actual_log.get(numOfCommitted);
                                //noinspection Duplicates
                                if (this.actual_db.containsKey(tmpLE.data.getFirst())) {
                                    if (tmpLE.Append) {
                                        String tmpS = this.actual_db.get(tmpLE.data.getFirst());
                                        tmpS = tmpS + tmpLE.data.getSecond();
                                        this.actual_db.replace(tmpLE.data.getFirst(), tmpS);
                                    } else {
                                        this.actual_db.replace(tmpLE.data.getFirst(), tmpLE.data.getSecond());
                                    }
                                } else {
                                    this.actual_db.put(tmpLE.data.getFirst(), tmpLE.data.getSecond());
                                }
                                this.numOfCommitted++;
                            }
                            this.actual_log.add(appendEntries.newLogEntry);
                        }
                        appendEntries.setStatus(AppendEntries.AppendEntriesStatus.REPLY);
                        appendEntries.success = true;
                        super.send(appendEntries.sender, appendEntries);
                    }

                    if (appendEntries.numOfCommitted > this.numOfCommitted && this.actual_log.size() > this.numOfCommitted) {
                        LogEntry tmpLE = this.actual_log.get(numOfCommitted);
                        //noinspection Duplicates
                        if (this.actual_db.containsKey(tmpLE.data.getFirst())) {
                            if (tmpLE.Append){
                                String tmpS = this.actual_db.get(tmpLE.data.getFirst());
                                tmpS = tmpS + tmpLE.data.getSecond();
                                this.actual_db.replace(tmpLE.data.getFirst(), tmpS);
                            } else {
                                this.actual_db.replace(tmpLE.data.getFirst(), tmpLE.data.getSecond());
                            }

                        } else {
                            this.actual_db.put(tmpLE.data.getFirst(), tmpLE.data.getSecond());
                        }
                        this.numOfCommitted++;
                    }




                    // 7.a Find if newLogEntry is already in the log
                  /*boolean found = false;
                  if (appendEntries.newLogEntry != null) {
                      for (LogEntry le : this.actual_log) {
                          if (le.requestId.equals(appendEntries.newLogEntry.requestId)) {
                              found = true;
                              break;
                          }
                      }
                  }
                  // 7.b Append any new entry not already in the log
                  if (!found && appendEntries.newLogEntry != null) {
                      this.actual_log.add(appendEntries.newLogEntry);
                      appendEntries.setStatus(AppendEntries.AppendEntriesStatus.REPLY);
                      appendEntries.success = true;
                      super.send(appendEntries.sender, appendEntries);


                  }*/
                    // 8. Advance state machine with newly committed entry

                } else {
                    // Step down if currentTerm changes
                    if (appendEntries.getTerm() > this.currentTerm) {
                        this.currentTerm = appendEntries.getTerm();
                        this.serverTimer = 5 + rn.nextInt(15);
                        this.votedFor = null;
                        this.serverStatus = ServerStatus.FOLLOWER;
                    } else {
                        if (appendEntries.success) {
                            // Update nextIndexes
                            for (Pair<String, Integer> p : this.nextIndexes) {
                                int tmp = p.getSecond();
                                p.setSecond(tmp+1);
                            }
                            for (LogEntry le : this.actual_log) {
                                if (le.requestId.equals(appendEntries.newLogEntry.requestId) && !le.committed) {
                                    le.votes++;
                                    // Mark log entries committed if stored on a majority of servers and
                                    // at least one entry from current ter is stored on a majority of servers
                                    if (le.votes > ((this.otherProcessesInCluster.size() / 2) + 1)) {
                                        //noinspection Duplicates
                                        if (this.actual_db.containsKey(le.data.getFirst())) {
                                            if (le.Append) {
                                                String tmpS = this.actual_db.get(le.data.getFirst());
                                                tmpS = tmpS + le.data.getSecond();
                                                this.actual_db.replace(le.data.getFirst(), tmpS);
                                            } else {
                                                this.actual_db.replace(le.data.getFirst(), le.data.getSecond());
                                            }
                                        } else {
                                            this.actual_db.put(le.data.getFirst(), le.data.getSecond());
                                        }
                                        this.numOfCommitted++;
                                        le.committed = true;
                                        super.send(le.msgSender, new ServerResponseConfirm(le.requestId));
                                    }
                                    break;
                                }
                            }
                        } else {
                            // If AppendEntries fails because of log inconsistency,
                            // decrement nextIndex and retry
                            if (this.serverStatus == ServerStatus.LEADER) {
                                for (Pair<String, Integer> p : this.nextIndexes) {
                                    if (p.getFirst().equals(appendEntries.sender)) {
                                        int tmp = p.getSecond();
                                        p.setSecond(tmp-1);
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (msg instanceof ClientRequestWhoIsLeader) {
                super.send(msg.sender, new ServerResponseLeader(((ClientRequestWhoIsLeader) msg).getRequestId(),
                        this.leaderId));
            } else if (msg instanceof ClientRequestWithContent) {
                if (this.serverStatus == ServerStatus.FOLLOWER || this.serverStatus == ServerStatus.CANDIDATE) {
                    super.send(msg.sender, new ServerResponseLeader(((ClientRequestWithContent) msg).getRequestId(),
                            this.leaderId));
                } else {
                    if (((ClientRequestWithContent) msg).getOperation().getName().equals("APPEND")) {
                        // LEADER: Accept commands from clients, append new entries to local log
                        ClientRequestWithContent msgTmp = (ClientRequestWithContent) msg;
                        boolean notFound = true;
                        for (LogEntry le : this.actual_log) {
                            if (le.requestId.equals(((ClientRequestWithContent) msg).getRequestId())) {
                                notFound = false;
                                break;
                            }
                        }
                        if (notFound) {
                            System.out.print(" > NEW APPEND ");
                            LogEntry logEntry = new LogEntry(msgTmp.getRequestId(), this.currentTerm,
                                    this.actual_log.size(),(Pair<String, String>) msgTmp.getContent(), msgTmp.sender);
                            logEntry.index = this.getLastLogIndex()+1;
                            this.actual_log.add(logEntry);

                            LogEntry prevLog;
                            if (this.getLastLogIndex() - 1 < 0) {
                                prevLog = null;
                            } else {
                                prevLog = this.actual_log.get(this.getLastLogIndex()-1);
                            }

                            for (String s : this.otherProcessesInCluster) {
                                LogEntry le = new LogEntry(logEntry.requestId, this.currentTerm, this.getLastLogIndex(),
                                        logEntry.data, msgTmp.sender);
                                AppendEntries ae = new AppendEntries(AppendEntries.AppendEntriesStatus.REQUEST,
                                        this.currentTerm, this.numOfCommitted, prevLog, le);
                                super.send(s, ae);
                            }


                        }
                    } else if (((ClientRequestWithContent) msg).getOperation().getName().equals("GET")) {
                        Pair<String, String> pair = (Pair<String, String>)((ClientRequestWithContent) msg).getContent();
                        super.send(msg.sender, new ServerResponseWithContent<>(((ClientRequestWithContent) msg).getRequestId(),
                                this.actual_db.get(pair.getFirst())));

                    } else {
                        ClientRequestWithContent msgTmp = (ClientRequestWithContent) msg;
                        boolean notFound = true;
                        for (LogEntry le : this.actual_log) {
                            if (le.requestId.equals(((ClientRequestWithContent) msg).getRequestId())) {
                                notFound = false;
                                break;
                            }
                        }
                        if (notFound) {
                            System.out.print(" > NEW PUT ");
                            LogEntry logEntry = new LogEntry(msgTmp.getRequestId(), this.currentTerm,
                                    this.actual_log.size(),(Pair<String, String>) msgTmp.getContent(), msgTmp.sender);
                            logEntry.index = this.getLastLogIndex()+1;
                            logEntry.Append = false;
                            this.actual_log.add(logEntry);

                            LogEntry prevLog;
                            if (this.getLastLogIndex() - 1 < 0) {
                                prevLog = null;
                            } else {
                                prevLog = this.actual_log.get(this.getLastLogIndex()-1);
                            }

                            for (String s : this.otherProcessesInCluster) {
                                LogEntry le = new LogEntry(logEntry.requestId, this.currentTerm, this.getLastLogIndex(),
                                        logEntry.data, msgTmp.sender);
                                le.Append = false;
                                AppendEntries ae = new AppendEntries(AppendEntries.AppendEntriesStatus.REQUEST,
                                        this.currentTerm, this.numOfCommitted, prevLog, le);
                                super.send(s, ae);
                            }


                        }


                    }
                }
            }
        }

        // Whenever last log index >= nextIndex for a follower,
        // send AppendEntries RPC with log entries starting at nextIndex


        if (this.serverTimer == 0) {
            this.serverTimer = 5 + rn.nextInt(15);
            this.serverStatus = ServerStatus.CANDIDATE;
            this.votedFor = super.getId();
            this.numOfSupporters = 1;
            this.currentTerm++;
            //System.out.println(super.getId() + " TIMEOUT Now I am CANDIDATE term " + this.currentTerm);
            for (String recipient : otherProcessesInCluster) {
                super.send(recipient, new RequestVote(this.currentTerm, this.getLogSize()));
            }
        }

        // Send initial empty AppendEntries RPC (heartbeat) to each follower,
        // repeating idle periods to prevent election timeouts
        if (this.heartBeatTimer == 0 && this.serverStatus == ServerStatus.LEADER) {
            this.heartBeatTimer = 4 - this.networkDelays;
            for (String recipient: this.otherProcessesInCluster) {
                AppendEntries ae;
                if (this.numOfCommitted == 0) {
                    ae = new AppendEntries(AppendEntries.AppendEntriesStatus.REQUEST, this.currentTerm, this.numOfCommitted,
                            null, null);
                } else {
                    ae = new AppendEntries(AppendEntries.AppendEntriesStatus.REQUEST, this.currentTerm, this.numOfCommitted,
                            this.actual_log.get(this.numOfCommitted-1), null);
                }
                super.send(recipient, ae);
            }
        }


      /*if (this.serverStatus == ServerStatus.LEADER) {
          for (Pair<String, Integer> p : this.nextIndexes) {
              if (this.getLastLogIndex() >= p.getSecond()) {
                  LogEntry prevLogEntry;
                  if (p.getSecond() == 0) {
                      prevLogEntry = null;
                  } else {
                      prevLogEntry = this.actual_log.get(p.getSecond()-1);
                  }
                  AppendEntries appendEntries = new AppendEntries(
                          AppendEntries.AppendEntriesStatus.REQUEST,
                          this.currentTerm,
                          this.numOfCommitted,
                          prevLogEntry,
                          this.actual_log.get(p.getSecond())
                  );
                  super.send(p.getFirst(), appendEntries);
              }
          }
      }*/


        if (this.repeatLogTimer == 0 && this.serverStatus == ServerStatus.LEADER) {
            this.repeatLogTimer = 4 - this.networkDelays;
            if (this.getLastLogIndex() > this.numOfCommitted-1) {
                LogEntry prevLog = null;
                if (this.numOfCommitted - 1 > 0) {
                    prevLog = this.actual_log.get(this.numOfCommitted-1);
                }
                for (String s : this.otherProcessesInCluster) {
                    AppendEntries ae = new AppendEntries(AppendEntries.AppendEntriesStatus.REQUEST,
                            this.currentTerm, this.numOfCommitted, prevLog,
                            this.actual_log.get(this.numOfCommitted));
                    super.send(s, ae);
                }
            }
        }

        //System.out.print(this.serverStatus);
        System.out.print(" Database " + super.getId() + ": ");
        for (Map.Entry<String, String> entry : this.actual_db.entrySet()) {
            System.out.print("<" + entry.getKey() + ", " + entry.getValue() + "> ");
        }
      /*System.out.print(" Log: " );
      for (LogEntry le : this.actual_log) {
          System.out.print(le.requestId + ", ");
      }*/
        System.out.println();

    }
}
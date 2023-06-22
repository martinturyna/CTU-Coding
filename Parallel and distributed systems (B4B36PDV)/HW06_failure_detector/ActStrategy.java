package cz.cvut.fel.agents.pdv.swim;
 
import cz.cvut.fel.agents.pdv.dsand.Message;
import cz.cvut.fel.agents.pdv.dsand.Pair;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.Random;
 
public class ActStrategy {
 
    private final int maxDelayForMessages;
    private final List<String> otherProcesses;
    private HashMap<String, Integer> pHashMap;
    private String currProcess;
    private String defProcess;
    private String send;
    private int procDelay;
    private int defDelay;
 
 
    public ActStrategy(int maxDelayForMessages, List<String> otherProcesses,
                       int timeToDetectKilledProcess, int upperBoundOnMessages) {
        this.maxDelayForMessages = maxDelayForMessages;
        this.otherProcesses = otherProcesses;
        this.pHashMap = new HashMap<>();
        this.currProcess = "";
        this.defProcess = "";
        this.send = "";
        this.procDelay = 0;
        this.defDelay = 0;
        for (String s : otherProcesses) {
            pHashMap.put(s, 15);
        }
    }
    public List<Pair<String, Message>> act(Queue<Message> inbox, String disseminationProcess) {
        ArrayList<Pair<String, Message>> output = new ArrayList<>();
        pHashMap.replaceAll((a, b) -> b+1);
        while (!inbox.isEmpty()) {
            Message m = inbox.poll();
            pHashMap.replace(m.sender, 0);
            if (m instanceof Ping) {
                output.add(new Pair<>(m.sender, new PingAck()));
            } else if (m instanceof PingAck) {
                if (m.sender.equals(currProcess)) {
                    currProcess = "";
                } else if (m.sender.equals(defProcess)) {
                    DeferredAck d = new DeferredAck(defProcess);
                    defProcess = "";
                    output.add(new Pair<>(send, d));
                    send = "";
                }
            } else if (m instanceof Deferred && defProcess.equals("")) {
                defProcess = ((Deferred) m).getS();
                defDelay = maxDelayForMessages;
                output.add(new Pair<>(((Deferred) m).getS(), new Ping()));
                send = m.sender;
            } else if (m instanceof DeferredAck && ((DeferredAck) m).getS().equals(currProcess)) {
                currProcess = "";
            }
        }
        if (currProcess.equals("")) {
            while (true) {
                String p = otherProcesses.get(new Random().nextInt(otherProcesses.size()));
                if (pHashMap.get(p) > 15) {
                    currProcess = p;
                    procDelay = maxDelayForMessages*6;
                    output.add(new Pair<>(currProcess, new Ping()));
                    break;
                }
            }
        } else {
            if (procDelay == 0) {
                output.add(new Pair<>(disseminationProcess, new PFDMessage(currProcess)));
                currProcess = "";
            } else {
                if (procDelay <= 5*maxDelayForMessages) {
                    output.add(new Pair<>(otherProcesses.get(new Random().nextInt(otherProcesses.size())), new Deferred(currProcess)));
                    output.add(new Pair<>(otherProcesses.get(new Random().nextInt(otherProcesses.size())), new Deferred(currProcess)));
                    output.add(new Pair<>(otherProcesses.get(new Random().nextInt(otherProcesses.size())), new Deferred(currProcess)));
                }
                procDelay--;
            }
        }
        if (!defProcess.equals("")) {
            if (defDelay == 0) {
                defProcess = "";
            } else {
                defDelay--;
            }
        }
        return output;
    }
}
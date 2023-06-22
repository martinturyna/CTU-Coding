package cz.cvut.fel.agents.pdv.exclusion;

import cz.cvut.fel.agents.pdv.clocked.ClockedMessage;

/**
 * Created by Martin on 12.05.2018.
 */
public class OkMessage extends ClockedMessage {

    private String criticalSectionName;

    public OkMessage(String criticalSectionName) {
        this.criticalSectionName = criticalSectionName;
    }

    public String getCriticalSectionName() {
        return this.criticalSectionName;
    }

}
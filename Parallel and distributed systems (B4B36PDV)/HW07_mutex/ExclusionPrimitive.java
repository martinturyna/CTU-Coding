package cz.cvut.fel.agents.pdv.exclusion;

import cz.cvut.fel.agents.pdv.clocked.ClockedMessage;
import cz.cvut.fel.agents.pdv.clocked.ClockedProcess;

import java.util.LinkedList;
import java.util.Queue;

public class ExclusionPrimitive {

    /**
     * Stavy, ve kterych se zamek muze nachazet.
     */
    enum AcquisitionState {
        RELEASED,    // Uvolneny   - Proces, ktery vlastni aktualni instanci ExclusionPrimitive o pristup do kriticke
        //              sekce nezada

        WANTED,      // Chteny     - Proces, ktery vlastni aktualni instanci ExclusionPrimitive zada o pristup do
        //              kriticke sekce. Ten mu ale zatim nebyl odsouhlasen ostatnimi procesy.

        HELD         // Vlastneny  - Procesu bylo prideleno pravo pristupovat ke sdilenemu zdroji.
    }

    private ClockedProcess owner;            // Proces, ktery vlastni aktualni instanci ExclusionPrimitive

    private String criticalSectionName;      // Nazev kriticke sekce. POZOR! V aplikaci se muze nachazet vice kritickych
    // sekci s ruznymi nazvy!

    private String[] allAccessingProcesses;  // Seznam vsech procesu, ktere mohou chtit pristupovat ke kriticke sekci
    // s nazvem 'criticalSectionName' (a tak spolurozhoduji o udelovani pristupu)

    private AcquisitionState state;          // Aktualni stav zamku (vzhledem k procesu 'owner').
    // state==HELD znamena, ze proces 'owner' muze vstoupit do kriticke sekce

    // Doplnte pripadne vlastni datove struktury potrebne pro implementaci
    // Ricart-Agrawalova algoritmu pro vzajemne vylouceni

    private String ownerID;
    private Queue<String> mutexQueue;
    private int OKcounter;
    private int timeOfRequest;

    public ExclusionPrimitive(ClockedProcess owner, String criticalSectionName, String[] allProcesses) {
        this.owner = owner;
        this.ownerID = owner.id;
        this.criticalSectionName = criticalSectionName;
        this.allAccessingProcesses = allProcesses;
        this.mutexQueue = new LinkedList<>();
        this.OKcounter = 0;
        this.state = AcquisitionState.RELEASED;
        this.timeOfRequest = Integer.MAX_VALUE;

    }

    /**
     * Metoda pro zpracovani nove prichozi zpravy
     *
     * @param m    prichozi zprava
     * @return 'true', pokud je zprava 'm' relevantni pro aktualni kritickou sekci.
     */
    public boolean accept(ClockedMessage m) {

        // Implementujte zpracovani prijimane zpravy informujici
        // o pristupech ke sdilenemu zdroji podle Ricart-Agrawalova
        // algoritmu. Pokud potrebujete posilat specificke zpravy,
        // vytvorte si pro ne vlastni tridy.
        //
        // POZOR! Ne vsechny zpravy, ktere v teto metode dostanete Vas
        // budou zajimat! Budou Vam prichazet i zpravy, ktere se  napriklad
        // tykaji jinych kritickych sekci. Pokud je zprava nerelevantni, tak
        // ji nezpracovavejte a vratte navratovou hodnotu 'false'. Nekdo jiny
        // se o ni urcite postara :-)
        //
        // Nezapomente se starat o cas procesu 'owner'
        // pomoci metody owner.increaseTime(). Aktualizaci
        // logickeho casu procesu s prijatou zpravou (pomoci maxima) jsme
        // za Vas jiz vyresili.
        //
        // Cas poslani prijate zpravy muzete zjistit dotazem na hodnotu
        // m.sentOn. Aktualni logicky cas muzete zjistit metodou owner.getTime().
        // Zpravy se posilaji pomoci owner.send(prijemce, zprava) a je jim auto-
        // maticky pridelen logicky cas odeslani. Retezec identifikujici proces
        // 'owner' je ulozeny v owner.id.

        if (m instanceof OkMessage && ((OkMessage) m).getCriticalSectionName().equals(this.criticalSectionName)) {
            this.OKcounter++;
            if (this.OKcounter == this.allAccessingProcesses.length - 1) {
                this.OKcounter = 0;
                this.state = AcquisitionState.HELD;
            }
            return true;
        } else if (m instanceof RequestMessage && ((RequestMessage) m).getCriticalSectionName().equals(this.criticalSectionName)) {
            if (this.state == AcquisitionState.HELD || (this.state == AcquisitionState.WANTED && this.timeOfRequest < m.sentOn || (this.timeOfRequest == m.sentOn && (((RequestMessage) m).sender.compareTo(this.ownerID)) > 0))) {
                this.mutexQueue.add(((RequestMessage) m).sender);
            } else {
                this.owner.increaseTime();
                this.owner.send(((RequestMessage) m).sender, new OkMessage(this.criticalSectionName));
            }
            return true;
        }
        return false;
    }

    public void requestEnter() {

        // Implementujte zadost procesu 'owner' o pristup
        // ke sdilenemu zdroji 'criticalSectionName'
        // Pokud chce proces Pi pozadat o vstup do kriticke sekce K, zaznamena cas Ti kdy
        // o zdroj zada a posle zpravu REQUEST(K) s timto casem vsem procesum, ktere do K
        // pristupuji. Nastavi stav sveho zamku K na WANTED.
        this.OKcounter = 0;
        this.owner.increaseTime();
        this.timeOfRequest = owner.getTime();
        this.state = AcquisitionState.WANTED;

        for (String p : this.allAccessingProcesses){
            if (!p.equals(this.ownerID)) {
                this.owner.send(p, new RequestMessage(this.criticalSectionName));
            }
        }
    }

    public void exit() {

        // Implementuje uvolneni zdroje, aby k nemu meli pristup i
        // ostatni procesy z 'allAccessingProcesses', ktere ke zdroji
        // mohou chtit pristupovat

        // Pokud proces Pi dokonci praci v kriticke sekci K, nastavi stav zamku K na RELEASED,
        // odpovi na vsechny zpravy ve fronte zamku a frontu vyprazdni.

        this.state = AcquisitionState.RELEASED;
        this.owner.increaseTime();
        while (!this.mutexQueue.isEmpty()) {
            this.owner.send(this.mutexQueue.poll(), new OkMessage(this.criticalSectionName));
        }
        this.timeOfRequest = Integer.MAX_VALUE;
    }

    public String getName() {
        return this.criticalSectionName;
    }

    public boolean isHeld() {
        return this.state == AcquisitionState.HELD;
    }

}

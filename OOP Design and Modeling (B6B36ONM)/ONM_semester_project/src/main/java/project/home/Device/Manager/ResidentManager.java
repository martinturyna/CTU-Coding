package project.home.Device.Manager;

import project.home.Device.Device;
import project.home.Resident.Animal;
import project.home.Resident.Man;
import java.util.ArrayList;

/**
 * ResidentManager handle all people actions every cycle
 */
public class ResidentManager {
    private ArrayList<Man> people;
    private ArrayList<Animal> animals;
    private boolean fire;
    private ArrayList<Device> brokenDevices = new ArrayList<Device>();

    public ResidentManager(ArrayList<Man> people, ArrayList<Animal> animals) {
        this.people = people;
        this.animals = animals;
        this.fire = false;
    }

    /**
     * project.Main method that is called in project.Simulation every cycle
     * to update all people actions. Firstly check if there is
     * any broken device. In that case first non-occupied man
     * is chosen to repair the device and remove it from pending
     * queue of broken devices. If there is no broken device anymore
     * then let the man do some random action.
     */
    public void updatePeople(){
        if (brokenDevices.size() > 0) {
            for(Man m : people) {
                if (!m.isOccupied()) {
                    m.repairDevice(brokenDevices.get(0));
                    brokenDevices.remove(0);
                    if (brokenDevices.size() == 0) {
                        break;
                    }
                }
            }
        }
        for (Man m : people) {
            m.doSomething();
        }
    }

    /**
     * updateAnimals method let the animal to do some random action.
     */
    public void updateAnimals(){
        for(Animal a : animals){
            a.doSomething();
        }
    }

    /**
     * addToBrokenDevices is method to be called from DeviceManager.
     * DeviceManager add broken device immediately when it is broken.
     */
    public void addToBrokenDevices(Device d) { brokenDevices.add(d); }

    /**
     * setFire method is called from DeviceManager in case of fire to notify
     * people to run and call fireman.
     */
    public void setFire(boolean b) { 
        fire = b;
        boolean tmp = true;
        for(Man m : people){
            if(tmp){
                m.fireCallFireman();
                tmp = false;
            }
            m.fireRun();
        }
    }
}

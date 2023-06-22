package project.home.Resident;

import project.home.Device.Device;
import project.home.Visitor.ManTurnOffDeviceVisitor;
import project.home.Home;
import project.home.SportResource.SportResource;
import project.home.room.Room;
import java.util.Random;

/**
 * abstract class Man represents main template for every human resident.
 * Contains all important methods such as doSomething, goSport, repairDevice.
 */
public abstract class Man {
    protected ManTurnOffDeviceVisitor mtdv;
    
    protected SportResource mySportResource = null;

    /**
     *  Represents current location of man in house.
     */
    protected Room location;

    /**
     * If this is true, it means that man
     *  already made some action this hour.
     */
    protected boolean occupied = false;

    /**
     *  Represents project.home man belongs to.
     */
    protected Home home;

    /**
     *  Represents devaci, what is being used by the man.
     *  
     */
    protected Device myDevice = null;


    /**
     *
     * @param home
     */
    public Man(Home home) {
        this.home = home;
        this.mtdv = new ManTurnOffDeviceVisitor();
    }
    
    /**
     * Returns the type of the man.
     * (Mother, Daughter, Father, Son)
     * @return
     */
    public abstract String getManType();
    
    /**
     *  Sets the current location of man.
     * @param room
     */
    public void setLocation(Room room){
        location = room;
    }
    
    /**
     * Returns man's current location.
     * @return
     */
    public Room getLocation(){
        return location;
    }

    /**
     * Method that repair broken device and add the action to reporter.
     */
    public void repairDevice(Device device){
        if(myDevice != null){
            myDevice.accept(mtdv);
            myDevice = null;
        }
        device.repair();
        home.getAur().deviceRepairReport(this, device);
    }
    
    /**
     * Man turns on given device.
     * @param device
     */
    public void turnOnDevice(Device device){
        device.turnOn();
    }
    
    /**
     *  Man turns off given device.
     * @param device
     */
    public void turnOffDevice(Device device){
        device.turnOff();
    }

    /**
     * This method iterate all sport resources. If some of them is
     * ready to be used, writes the report as the action.
     */
    public boolean goSport(){
        for(SportResource s : home.getSportResources()){
            if(s.goSport()){
                mySportResource = s;
                home.getAur().sportReport(this, s);
                return true;
            }
        }
        return false;
    }

    /**
     * changeLocation method handle random movement of human resident
     * in the house. Randomly choose the floor and room destination.
     * Finally writes the report.
     */
    public void changeLocation(){
        Random rand = new Random();
        Room from = location;
        int tmp = rand.nextInt(home.getFloors().size());
        int tmp2 = rand.nextInt(home.getFloors().get(tmp).getRooms().size());
        location = home.getFloors().get(tmp).getRooms().get(tmp2);
        home.getAur().changeLocationReport(this, from, location);
    }
    
    /**
     * useDevice method handle device usage for human resident.
     * If someone decide to use device, this method detects all
     * devices in the room and randomly choose one of them
     * to be used.
     */
    public void useDevice(){
        Random rand = new Random();
        int tmp;
        tmp = 0;
        for(Device d : location.getDevices()){
            if(d.isIsWorking()){
               tmp = 1;
               break;
            }
        }
        if(tmp == 0){
            return;
        }
        while(true){
            tmp = rand.nextInt(location.getDevices().size());
            myDevice = location.getDevices().get(tmp);
            if(myDevice.isIsWorking()){
                break;
            }
        }
        location.getDevices().get(tmp).turnOn();
        home.getAur().deviceUsageReport(this, myDevice);
    }

    /**
     * project.Main method that basically handle type of action for human
     * to be done. If in previous cycle has been any device used,
     * it turns it off firstly.
     * After that checks if man should be sleeping, otherwise
     * let randomly decide between sport, use device or change location.
     * probability correspond with task (50:50)
     */
    public void doSomething(){
        if(this.mySportResource != null){
            mySportResource.goBackHome();
            mySportResource = null;
        }
        if(myDevice != null){
            myDevice.accept(mtdv);
            myDevice = null;
        } 
        int time = home.getWorldState().getHour();
        if(time > 22 || time < 8){
            occupied = true;
            home.getAur().sleepReport(this);
            return;
        }
        if(occupied){
            occupied = false;
        }
        Random rand = new Random();
        int tmp = rand.nextInt(2);
        int check = 0;
        if(tmp == 0){
            boolean tmpB = this.goSport();
            if(tmpB){
                check = 1;
            }
        }
        if(tmp == 1 || check == 0){
            tmp = rand.nextInt(2);
            if(tmp == 0){
                this.useDevice();
            }
            if(tmp == 1){
                this.changeLocation();
            }
        }
    }
    
    /**
     * Man feeds given animal.
     * @param animal
     */
    public void feedAnimal(Animal animal){
        animal.Feed();
        home.getAur().feedReport(animal, this);
    }
    
    /**
     * Man walks given animal.
     * @param animal
     */
    public void walkAnimal(Animal animal){
        animal.goForWalk();
        home.getAur().walkAnimalReport(this, animal);
    }

    /**
     *  Man runs out when house is on fire.
     */
    public void fireRun(){
        
    }

    /**
     *  Man calls Firemen when house is on fire.
     */
    public void fireCallFireman(){
        
    }

    /**
     * Returns true if man already have done something this hour.
     * @return
     */
    public boolean isOccupied() {
        return occupied;
    }

    /**
     * Returns man's project.home.
     * @return
     */
    public Home getHome() {
        return home;
    }
}

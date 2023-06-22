package project.home.Device;

import project.home.Visitor.DeviceVisitor;
import project.home.room.Room;

/**
 * Abstract class Device offers useful methods for every single
 * device that extends it.
 */
public abstract class Device {
    protected DeviceState state;
    protected Room location;
    protected boolean manualRdy = false;
    protected boolean isWorking = true;

    public Device(Room location){
        this.location = location;
    }

    public boolean isIsWorking() {
        return isWorking;
    }

    public void setIsWorking(boolean isWorking) {
        this.isWorking = isWorking;
    }

    public void breakDevice(){
        state.breakDevice();
    }

    public void setState(DeviceState state){
        this.state = state;
    }

    public void turnOn() {
        state.turnOn();
    }

    public void turnOff(){
        state.turnOff();
    }

    public void turnIdle() {
        state.turnIdle();
    }

    public void updateLifetime(){
        state.updateLifetime();
    }

    public abstract int getLifetime();

    public abstract void setLifetime(int lifetime);

    public int getEnergyUse(){
        return state.getEnergyUse();
    }

    public abstract int getOnEnergyUse();

    public abstract int getIdleEnergyUse();

    public abstract int getOffEnergyUse();

    public int getWaterUse(){
        return state.getWaterUse();
    }

    public int getOnWaterUse(){
        return 0;
    }

    public abstract String getDeviceType();

    public Room getLocation() {
        return location;
    }

    public void setLocation(Room location) {
        this.location = location;
    }

    private void findManual(){
        manualRdy = true;
    }

    public DeviceState getState() {
        return state;
    }

    /**
     * method for repairing device if it is broken.
     * Firstly man must find manual and read it, after
     * that he is ready to repairing.
     */
    public void repair(){
        this.findManual();
        state.repairDevice();
        this.setLifetime(1000);
        isWorking = true;
    }
    
    public abstract void accept(DeviceVisitor dv);

}
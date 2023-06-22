package project.home.Facilities;

import project.home.Visitor.FacilityVisitor;
import project.home.room.Room;

/**
 *
 * @author �t�p�n Otta
 */
public abstract class Facility {
    protected FacilityState state;
    protected Room location;
    
    public void setState(FacilityState state){
        this.state = state;
    }

    public void turnOn() {
        state.turnOn();
    }

    public void turnOff(){
        state.turnOff();
    }

    public FacilityState getState() {
        return state;
    }

    public Room getLocation() {
        return location;
    }
    
    public int getEnergyUse(){
        return state.getEnergyUse();
    }

    public abstract int getOnEnergyUse();

    public abstract int getOffEnergyUse();

    public abstract String getDeviceType();
    
    public abstract void accept(FacilityVisitor fv);
}

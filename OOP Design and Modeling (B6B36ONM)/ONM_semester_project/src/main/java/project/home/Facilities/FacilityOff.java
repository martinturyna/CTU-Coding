package project.home.Facilities;

/**
 *
 * @author �t�p�n Otta
 */
public class FacilityOff implements FacilityState {
    private final Facility facility;

    public FacilityOff(Facility facility) {
        this.facility = facility;
    }

    public void turnOn() {
        facility.setState(new FacilityOn(this.facility));
    }

    public void turnOff() {
        
    }

    public int getEnergyUse() {
        return this.facility.getOffEnergyUse();
    }
    
}

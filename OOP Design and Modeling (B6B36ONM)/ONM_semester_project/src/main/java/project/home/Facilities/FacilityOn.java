package project.home.Facilities;

/**
 *
 * @author �t�p�n Otta
 */
public class FacilityOn implements FacilityState {
    private final Facility facility;

    public FacilityOn(Facility facility) {
        this.facility = facility;
    }
    
    public void turnOn() {
        
    }

    public void turnOff() {
        facility.setState(new FacilityOff(facility));
    }

    public int getEnergyUse() {
        return this.facility.getOnEnergyUse();
    }
    
}

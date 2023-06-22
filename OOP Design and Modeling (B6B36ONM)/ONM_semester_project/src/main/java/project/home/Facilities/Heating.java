package project.home.Facilities;

import project.home.Visitor.FacilityVisitor;

/**
 * AirConditioner is facility that is dependant on worldState temperature
 */
public class Heating extends Facility {
    private final int OnEnergyUse = 90;
    private final int OffEnergyUse = 0;
    private final String deviceType = "Heating";

    public Heating() {
        super.state = new FacilityOff(this);
    }

    @Override
    public int getOnEnergyUse() {
        return this.OnEnergyUse;
    }

    @Override
    public int getOffEnergyUse() {
        return this.OffEnergyUse;
    }

    @Override
    public String getDeviceType() {
        return this.deviceType;
    }

    @Override
    public void accept(FacilityVisitor fv) {
        fv.visitHeating(this);
    }
    
}

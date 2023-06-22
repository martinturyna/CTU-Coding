package project.home.Facilities;

import project.home.Visitor.FacilityVisitor;

/**
 * AirConditioner is facility that is dependant on worldState temperature
 */
public class AirConditioner extends Facility {
    private final int OnEnergyUse = 85;
    private final int OffEnergyUse = 0;
    private final String deviceType = "AirConditioner";

    public AirConditioner() {
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
        fv.visitAirConditioner(this);
    }
}

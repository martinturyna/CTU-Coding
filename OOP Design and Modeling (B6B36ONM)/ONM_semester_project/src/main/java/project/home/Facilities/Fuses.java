package project.home.Facilities;

import project.home.Visitor.FacilityVisitor;

/**
 * In case of lightning the fuses could be turned off.
 */
public class Fuses extends Facility {
    private final int OnEnergyUse = 0;
    private final int OffEnergyUse = 0;
    private final String deviceType = "Fuses";

    public Fuses() {
        super.state = new FacilityOn(this);
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
        fv.visitFuses(this);
    }

}

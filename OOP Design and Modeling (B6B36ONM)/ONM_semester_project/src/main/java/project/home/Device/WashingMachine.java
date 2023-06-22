package project.home.Device;

import project.home.Visitor.DeviceVisitor;
import project.home.room.Room;

/**
 *
 * @author �t�p�n Otta
 */
public class WashingMachine extends Device{
    private final int OnEnergyUse = 50;
    private final int IdleEnergyUse = 23;
    private final int OffEnergyUse = 0;
    private int lifetime=100;

    private final int onWaterUse = 20;

    private static String deviceType = "Washing machine";

    public WashingMachine(Room location) {
        super(location);
        super.state = new DeviceOff(this);
    }

    public int getOnEnergyUse() {
        return OnEnergyUse;
    }

    public int getIdleEnergyUse() {
        return IdleEnergyUse;
    }

    public int getOffEnergyUse() {
        return OffEnergyUse;
    }

    public int getOnWaterUse() {
        return onWaterUse;
    }

    public String getDeviceType() {
        return deviceType;
    }

    @Override
    public int getLifetime() {
        return this.lifetime;
    }

    @Override
    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
    }

    @Override
    public void accept(DeviceVisitor dv) {
        dv.visitWashingMachine(this);
    }
}

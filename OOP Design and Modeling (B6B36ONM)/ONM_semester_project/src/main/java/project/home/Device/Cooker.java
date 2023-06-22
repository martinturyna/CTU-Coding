package project.home.Device;

import project.home.Visitor.DeviceVisitor;
import project.home.room.Room;

/**
 *
 * @author �t�p�n Otta
 */
public class Cooker extends Device {
    private final int OnEnergyUse = 40;
    private final int IdleEnergyUse = 8;
    private final int OffEnergyUse = 0;
    private int lifetime = 100;
    private static String deviceType = "Cooker";

    public Cooker(Room location) {
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
        dv.visitCooker(this);
    }
    
}

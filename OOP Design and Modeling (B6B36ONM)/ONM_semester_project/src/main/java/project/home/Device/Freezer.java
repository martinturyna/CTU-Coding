package project.home.Device;

import project.home.Visitor.DeviceVisitor;
import project.home.room.Room;

/**
 *
 * @author �t�p�n Otta
 */
public class Freezer extends Device {
    private final int OnEnergyUse = 55;
    private final int IdleEnergyUse = 26;
    private final int OffEnergyUse = 0;
    private int lifetime = 100;
    private static String deviceType = "Freezer";

    public Freezer(Room location) {
        super(location);
        super.state = new DeviceIdle(this);
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
        dv.visitFreezer(this);
    }
    
}

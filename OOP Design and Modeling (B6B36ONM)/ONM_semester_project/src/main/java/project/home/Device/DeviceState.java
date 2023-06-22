package project.home.Device;

/**
 *
 * @author �t�p�n Otta
 */
public interface DeviceState {
    
    public void turnOn();
    
    public void turnIdle();
    
    public void turnOff();
    
    public int getEnergyUse();

    public int getWaterUse();
    
    public void updateLifetime();
    
    public void breakDevice();
    
    public void repairDevice();
}

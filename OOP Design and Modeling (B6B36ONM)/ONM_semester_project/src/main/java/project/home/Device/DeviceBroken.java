package project.home.Device;

/**
 *
 * @author �t�p�n Otta
 */
public class DeviceBroken implements DeviceState {
    private final Device device;

    public DeviceBroken(Device device) {
        this.device = device;
        device.setIsWorking(false);
    }

    public void turnOn() {   
    }

    public void turnIdle() {   
    }

    public void turnOff() {
    }
    
    public void repairDevice(){
        device.setState(new DeviceOff(device));
    }

    public int getEnergyUse() {
        return 0;
    }

    public int getWaterUse() {
        return 0;
    }

    public void updateLifetime() {
        
    }

    public void breakDevice() {
    }
    
}

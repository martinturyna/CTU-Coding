package project.home.Device;

/**
 *
 * @author �t�p�n Otta
 */
public class DeviceIdle implements DeviceState {
    private final Device device;
    private int energyUse;

    public DeviceIdle(Device device) {
        this.device = device;
        this.setDeviceEnergyUse();
    }
    
    private void setDeviceEnergyUse(){
        energyUse = device.getIdleEnergyUse();
    }
    
    public void turnOn() {
        device.setState(new DeviceOn(device));
    }

    public void turnIdle() {
        
    }

    public void turnOff() {
        device.setState(new DeviceOff(device));
    }

    public int getEnergyUse() {
        return this.energyUse;
    }

    public int getWaterUse() {
        return 0;
    }

    public void updateLifetime() {
        device.setLifetime(device.getLifetime()-2);     
        if(device.getLifetime() <= 0){
            device.setState(new DeviceBroken(device));
        }
    }

    public void breakDevice() {
        device.setState(new DeviceBroken(device));
    }

    public void repairDevice() {
    }
    
}

package project.home.Device;

/**
 *
 * @author �t�p�n Otta
 */
public class DeviceOn implements DeviceState {
    private final Device device;
    private int energyUse;

    public DeviceOn(Device device) {
        this.device = device;
        this.setDeviceEnergyUse();
        
    }
    
    private void setDeviceEnergyUse(){
        energyUse = device.getOnEnergyUse();
    }

    public void turnOn() {
    }

    public void turnIdle() {
        device.setState(new DeviceIdle(device));
    }

    public void turnOff() {
        device.setState(new DeviceOff(device));
    }

    public int getEnergyUse() {
        return this.energyUse;
    }

    public int getWaterUse() {
        return device.getOnWaterUse();
    }

    public void updateLifetime() {
        device.setLifetime(device.getLifetime()-5);     
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

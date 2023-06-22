package project.home.Device;

/**
 *
 * @author �t�p�n Otta
 */
public class DeviceOff implements DeviceState{
    private final Device device;
    private int energyUse;
    
    public DeviceOff(Device device) {
        this.device = device;
        this.setDeviceEnergyUse();
    }
    
    private void setDeviceEnergyUse(){
        energyUse = device.getOffEnergyUse();
    }
    
    public void turnOn() {
        device.setState(new DeviceOn(device));
    }

    public void turnIdle() {
        device.setState(new DeviceIdle(device));
    }

    public void turnOff() {
    }

    public int getEnergyUse() {
        return this.energyUse;
    }

    public int getWaterUse() {
        return 0;
    }

    public void updateLifetime() {
        device.setLifetime(device.getLifetime()-0);     
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

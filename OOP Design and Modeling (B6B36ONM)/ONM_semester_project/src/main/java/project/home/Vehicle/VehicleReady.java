package project.home.Vehicle;

/**
 *
 * @author �t�p�n Otta
 */
public class VehicleReady implements VehicleState {
    private Vehicle vehicle;

    public VehicleReady(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void use() {
        vehicle.setState(new VehicleInUse(vehicle));
    }

    public void makeReady() {
    }
    
}

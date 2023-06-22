package project.home.Vehicle;

/**
 *
 * @author �t�p�n Otta
 */
public class VehicleInUse implements VehicleState {
    private Vehicle vehicle;

    public VehicleInUse(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void use() {
    }

    public void makeReady() {
        vehicle.setState(new VehicleReady(vehicle));
    }
    
}

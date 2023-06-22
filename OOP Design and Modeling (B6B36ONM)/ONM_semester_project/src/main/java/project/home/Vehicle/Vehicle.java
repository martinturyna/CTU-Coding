package project.home.Vehicle;


public abstract class Vehicle {
    protected VehicleState state;

    public Vehicle() {
        state = new VehicleReady(this);
    }
    
    public void drive(){
        state.use();
    }
    
    public void goBackHome(){
        state.makeReady();
    }
    
    public abstract String getVehicleType();
    
    public void setState(VehicleState state){
        this.state = state;
    }
}

package project.home;

import project.Reporter.ActivityAndUsageReporter;
import project.Time.WorldState;
import project.home.Device.Device;
import project.home.Device.Sensor.FireSensor;
import project.home.Device.Sensor.WeatherSensor;
import project.home.Facilities.Facility;
import project.home.Floor.Floor;
import project.home.Resident.Animal;
import project.home.Resident.Man;
import project.home.SportResource.SportResource;
import project.home.Vehicle.Vehicle;
import project.home.Window.Window;
import project.home.room.Room;

import java.util.ArrayList;

public class Home {

    private ArrayList<Floor> floors;
    private ArrayList<Vehicle> vehicles;
    private ArrayList<SportResource> sportResources;
    private ArrayList<Device> devices;
    private ArrayList<Window> windows;
    private WorldState worldState;
    private FireSensor fireSensor;
    private WeatherSensor weatherSensor;
    private ActivityAndUsageReporter aur;
    private ArrayList<Facility> facilities;
    private ArrayList<Man> people;
    private ArrayList<Animal> animals;

    public void setFloors(ArrayList<Floor> floors) {
        this.floors = floors;
    }

    public ArrayList<Facility> getFacilities() {
        return facilities;
    }

    public void setFacilities(ArrayList<Facility> facilities) {
        this.facilities = facilities;
    }

    public void setPeople(ArrayList<Man> people) {
        this.people = people;
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(ArrayList<Animal> animals) {
        this.animals = animals;
    }

    public ArrayList<Man> getPeople() {
        return people;
    }

    public void setVehicles(ArrayList<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public void setSportResources(ArrayList<SportResource> sportResources) {
        this.sportResources = sportResources;
    }

    public void setDevices(ArrayList<Device> devices) {
        this.devices = devices;
    }

    public void setWindows(ArrayList<Window> windows) {
        this.windows = windows;
    }

    public ArrayList<Floor> getFloors() {
        return floors;
    }

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    public ArrayList<SportResource> getSportResources() {
        return sportResources;
    }

    public ArrayList<Device> getDevices() {
        return devices;
    }

    public ArrayList<Window> getWindows() {
        return windows;
    }

    public void setWorldState(WorldState worldState){
        this.worldState = worldState;
        aur.setWs(worldState);
    }

    public WorldState getWorldState(){
        return worldState;
    }

    public FireSensor getFireSensor() {
        return fireSensor;
    }

    public void setFireSensor(FireSensor fireSensor) {
        this.fireSensor = fireSensor;
    }

    public WeatherSensor getWeatherSensor() {
        return weatherSensor;
    }

    public void setWeatherSensor(WeatherSensor weatherSensor) {
        this.weatherSensor = weatherSensor;
    }

    public ActivityAndUsageReporter getAur() {
        return aur;
    }

    public void setAur(ActivityAndUsageReporter aur) {
        this.aur = aur;
    }

    public Room getConcreteRoom(String roomType){
        Room ret = null;
        for(Floor f : floors){
            for(Room r : f.getRooms()){
                if(r.getRoomType() == roomType) ret = r;
            }
        }
        return ret;
    }

}

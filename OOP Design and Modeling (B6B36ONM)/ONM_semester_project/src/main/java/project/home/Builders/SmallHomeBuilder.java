package project.home.Builders;


import project.home.Device.Device;
import project.home.Device.Sensor.FireSensor;
import project.home.Device.Sensor.WeatherSensor;
import project.home.Facilities.AirConditioner;
import project.home.Facilities.Facility;
import project.home.Facilities.Fuses;
import project.home.Facilities.Heating;
import project.home.Floor.Floor;
import project.home.Home;
import project.home.Resident.*;
import project.home.SportResource.SportResource;
import project.home.Vehicle.Car;
import project.home.Vehicle.Vehicle;
import project.home.Window.Window;
import project.home.room.*;

import java.util.ArrayList;

/**
 * SmallHomeBuilder builds a small project.home consisting of only
 * one floor, 4 rooms, 2 residents and a car.
 */
public class SmallHomeBuilder implements HomeBuilder {
    ArrayList<Device> devices = new ArrayList<Device>();
    ArrayList<Window> windows = new ArrayList<Window>();
    RoomCreator roomCreator = new RoomCreator();
    private Home home;

    public SmallHomeBuilder(){
        this.home = new Home();
    }

    public void buildFloors() {
        ArrayList<Floor> floors = new ArrayList<Floor>();
        Floor floor = new Floor();
        floor.setFloorNumber(0);
        ArrayList<Room> rooms = new ArrayList<Room>();
        rooms.add(roomCreator.createKitchen());
        rooms.add(roomCreator.createBathroom());
        rooms.add(roomCreator.createLivingRoom());
        rooms.add(roomCreator.createBedroom());
        floor.setRooms(rooms);
        floors.add(floor);
        home.setFloors(floors);
    }

    public void buildVehicles() {
        ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
        vehicles.add(new Car());
        home.setVehicles(vehicles);
    }

    public void buildResidents() {
        ArrayList<Man> people = new ArrayList<Man>();
        ArrayList<Animal> animals = new ArrayList<Animal>();
        Father f = new Father(home);
        Mother m = new Mother(home);
        Daughter d = new Daughter(home);
        f.setLocation(home.getFloors().get(0).getRooms().get(0));
        m.setLocation(home.getFloors().get(0).getRooms().get(0));
        d.setLocation(home.getFloors().get(0).getRooms().get(0));
        people.add(f);
        people.add(m);
        people.add(d);
        home.setPeople(people);
        Cat c = new Cat(home);
        c.setLocation(home.getFloors().get(0).getRooms().get(0));
        animals.add(c);
        home.setAnimals(animals);
    }

    public void buildSportResources() {
        ArrayList<SportResource> sportResources = new ArrayList<SportResource>();
        home.setSportResources(sportResources);
    }

    public void buildWindows() {
        for (Floor f: home.getFloors()) {
            for (Room r: f.getRooms()) {
                windows.addAll(r.getWindows());
            }
        }
        home.setWindows(windows);
    }

    public void buildDevices() {
        for (Floor f: home.getFloors()) {
            for (Room r: f.getRooms()) {
                devices.addAll(r.getDevices());
            }
        }
        home.setDevices(devices);
    }
    
    public void buildFacilities(){
        ArrayList<Facility> facilities = new ArrayList<Facility>();
        facilities.add(new Heating());
        facilities.add(new AirConditioner());
        facilities.add(new Fuses());
        home.setFacilities(facilities);
    }

    public void buildFireSensor() {
        home.setFireSensor(new FireSensor(home.getFloors()));
    }

    public void buildWeatherSensor() {
        home.setWeatherSensor(new WeatherSensor());
    }

    public Home getResult() {
        return home;
    }
}

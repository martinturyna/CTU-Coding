package project.home.Builders;

import project.home.Device.Device;
import project.home.Facilities.Heating;
import project.home.Device.Sensor.FireSensor;
import project.home.Device.Sensor.WeatherSensor;
import project.home.Facilities.AirConditioner;
import project.home.Facilities.Facility;
import project.home.Facilities.Fuses;
import project.home.Floor.Floor;
import project.home.Home;
import project.home.Resident.*;
import project.home.SportResource.Bike;
import project.home.SportResource.Ski;
import project.home.SportResource.SportResource;
import project.home.Vehicle.Car;
import project.home.Vehicle.Vehicle;
import project.home.Window.Window;
import project.home.room.*;

import java.util.ArrayList;

/**
 * Large project.home builder builds a large project.home with 2 floors,
 * 9 residents (6 people + 3 animals), 8 rooms.
 * As for Sport equipment, large project.home has a ski and 2 bikes.
 */
public class LargeHomeBuilder implements HomeBuilder {
    ArrayList<Device> devices = new ArrayList<Device>();
    ArrayList<Window> windows = new ArrayList<Window>();
    RoomCreator roomCreator = new RoomCreator();
    Room startingLocation;
    Home home = new Home();

    public void buildFloors() {
        ArrayList<Floor> floors = new ArrayList<Floor>();

        Floor floor1 = new Floor(); floor1.setFloorNumber(0);
        ArrayList<Room> roomsOnFloor1 = new ArrayList<Room>();
        startingLocation = roomCreator.createKitchen();
        roomsOnFloor1.add(startingLocation);
        roomsOnFloor1.add(roomCreator.createBathroom());
        roomsOnFloor1.add(roomCreator.createLivingRoom());
        roomsOnFloor1.add(roomCreator.createBedroom());
        floor1.setRooms(roomsOnFloor1);

        Floor floor2 = new Floor(); floor2.setFloorNumber(1);
        ArrayList<Room> roomsOnFloor2 = new ArrayList<Room>();
        roomsOnFloor2.add(roomCreator.createBedroom());
        roomsOnFloor2.add(roomCreator.createBedroom());
        roomsOnFloor2.add(roomCreator.createBathroom());
        roomsOnFloor2.add(roomCreator.createWorkroom());
        floor2.setRooms(roomsOnFloor2);

        floors.add(floor1); floors.add(floor2);
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
        f.setLocation(startingLocation);
        Mother m = new Mother(home);
        m.setLocation(startingLocation);
        Daughter d = new Daughter(home);
        d.setLocation(startingLocation);
        Son s = new Son(home);
        s.setLocation(startingLocation);
        people.add(f); people.add(m);
        people.add(d); people.add(s);

        Cat c = new Cat(home);
        c.setLocation(startingLocation);
        Dog doggo = new Dog(home);
        doggo.setLocation(startingLocation);
        animals.add(c); animals.add(doggo);

        home.setPeople(people);
        home.setAnimals(animals);
    }

    public void buildSportResources() {
        ArrayList<SportResource> sportResources = new ArrayList<SportResource>();
        sportResources.add(new Ski());
        sportResources.add(new Bike()); sportResources.add(new Bike());
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
        facilities.add(new Fuses());
        facilities.add(new AirConditioner());
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

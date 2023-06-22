package project.home;

import project.Reporter.ActivityAndUsageReporter;
import project.Time.WorldState;
import project.home.Device.Device;
import project.home.Device.Sensor.FireSensor;
import project.home.Device.Sensor.WeatherSensor;
import project.home.Facilities.Facility;
import project.home.Facilities.Heating;
import project.home.Floor.Floor;
import project.home.Resident.Animal;
import project.home.Resident.Man;
import project.home.SportResource.SportResource;
import project.home.Vehicle.Vehicle;
import project.home.Window.Window;
import project.home.room.Room;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author �t�p�n Otta
 */
public class HomeTest {
    
    public HomeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of setFloors method, of class Home.
     */
    @Test
    public void testSetFloors() {
        System.out.println("setFloors");
        ArrayList<Floor> floors = new ArrayList<Floor>();
        floors.add(new Floor());
        Home instance = new Home();
        instance.setFloors(floors);
        assertEquals(instance.getFloors(), floors);
    }

    /**
     * Test of getFacilities method, of class Home.
     */
    @Test
    public void testGetFacilities() {
        System.out.println("getFacilities");
        Home instance = new Home();
        ArrayList<Facility> expResult = new ArrayList<Facility>();
        expResult.add(new Heating());
        instance.setFacilities(expResult);
        ArrayList<Facility> result = instance.getFacilities();
        assertEquals(expResult, result);
    }

    /**
     * Test of setFacilities method, of class Home.
     */
    @Test
    public void testSetFacilities() {
        System.out.println("setFacilities");
        ArrayList<Facility> facilities = new ArrayList<Facility>();
        Home instance = new Home();
        instance.setFacilities(facilities);
        assertEquals(instance.getFacilities(), facilities);
    }

    /**
     * Test of setPeople method, of class Home.
     */
    @Test
    public void testSetPeople() {
        System.out.println("setPeople");
        ArrayList<Man> people = new ArrayList<Man>();
        Home instance = new Home();
        instance.setPeople(people);
        assertEquals(instance.getPeople(), people);
    }

    /**
     * Test of getAnimals method, of class Home.
     */
    @Test
    public void testGetAnimals() {
        System.out.println("getAnimals");
        Home instance = new Home();
        ArrayList<Animal> expResult = new ArrayList<Animal>();
        instance.setAnimals(expResult);
        ArrayList<Animal> result = instance.getAnimals();
        assertEquals(expResult, result);
    }

    /**
     * Test of setAnimals method, of class Home.
     */
    @Test
    public void testSetAnimals() {
        System.out.println("setAnimals");
        ArrayList<Animal> animals = new ArrayList<Animal>();
        Home instance = new Home();
        instance.setAnimals(animals);
        assertEquals(animals, instance.getAnimals());
    }

    /**
     * Test of getPeople method, of class Home.
     */
    @Test
    public void testGetPeople() {
        System.out.println("getPeople");
        Home instance = new Home();
        ArrayList<Man> expResult = new ArrayList<Man>();
        instance.setPeople(expResult);
        ArrayList<Man> result = instance.getPeople();
        assertEquals(expResult, result);
    }

    /**
     * Test of setVehicles method, of class Home.
     */
    @Test
    public void testSetVehicles() {
        System.out.println("setVehicles");
        ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
        Home instance = new Home();
        instance.setVehicles(vehicles);
        assertEquals(instance.getVehicles(), vehicles);
    }

    /**
     * Test of setSportResources method, of class Home.
     */
    @Test
    public void testSetSportResources() {
        System.out.println("setSportResources");
        ArrayList<SportResource> sportResources = new ArrayList<SportResource>();
        Home instance = new Home();
        instance.setSportResources(sportResources);
        assertEquals(sportResources, instance.getSportResources());
    }

    /**
     * Test of setDevices method, of class Home.
     */
    @Test
    public void testSetDevices() {
        System.out.println("setDevices");
        ArrayList<Device> devices = null;
        Home instance = new Home();
        instance.setDevices(devices);
    }

    /**
     * Test of setWindows method, of class Home.
     */
    @Test
    public void testSetWindows() {
        System.out.println("setWindows");
        ArrayList<Window> windows = new ArrayList<Window>();
        Home instance = new Home();
        instance.setWindows(windows);
        assertEquals(windows, instance.getWindows());
    }

    /**
     * Test of getFloors method, of class Home.
     */
    @Test
    public void testGetFloors() {
        System.out.println("getFloors");
        Home instance = new Home();
        ArrayList<Floor> expResult = new ArrayList<Floor>();
        instance.setFloors(expResult);
        ArrayList<Floor> result = instance.getFloors();
        assertEquals(expResult, result);
    }

    /**
     * Test of getVehicles method, of class Home.
     */
    @Test
    public void testGetVehicles() {
        System.out.println("getVehicles");
        Home instance = new Home();
        ArrayList<Vehicle> expResult = new ArrayList<Vehicle>();
        instance.setVehicles(expResult);
        ArrayList<Vehicle> result = instance.getVehicles();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSportResources method, of class Home.
     */
    @Test
    public void testGetSportResources() {
        System.out.println("getSportResources");
        Home instance = new Home();
        ArrayList<SportResource> expResult = new ArrayList<SportResource>();
        instance.setSportResources(expResult);
        ArrayList<SportResource> result = instance.getSportResources();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDevices method, of class Home.
     */
    @Test
    public void testGetDevices() {
        System.out.println("getDevices");
        Home instance = new Home();
        ArrayList<Device> expResult = new ArrayList<Device>();
        instance.setDevices(expResult);
        ArrayList<Device> result = instance.getDevices();
        assertEquals(expResult, result);
    }

    /**
     * Test of getWindows method, of class Home.
     */
    @Test
    public void testGetWindows() {
        System.out.println("getWindows");
        Home instance = new Home();
        ArrayList<Window> expResult = new ArrayList<Window>();
        instance.setWindows(expResult);
        ArrayList<Window> result = instance.getWindows();
        assertEquals(expResult, result);
    }

    /**
     * Test of setWorldState method, of class Home.
     */
    @Test
    public void testSetWorldState() {
        System.out.println("setWorldState");
        WorldState worldState = new WorldState(2016, 1, 3, 0, 100);
        Home instance = new Home();
        ActivityAndUsageReporter aur = new ActivityAndUsageReporter();
        instance.setAur(aur);
        instance.setWorldState(worldState);
        assertEquals(instance.getWorldState(), worldState);
    }

    /**
     * Test of getWorldState method, of class Home.
     */
    @Test
    public void testGetWorldState() {
        System.out.println("getWorldState");
        Home instance = new Home();
        ActivityAndUsageReporter aur = new ActivityAndUsageReporter();
        instance.setAur(aur);
        WorldState expResult = new WorldState(2016, 1, 3, 0, 100);
        instance.setWorldState(expResult);
        WorldState result = instance.getWorldState();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFireSensor method, of class Home.
     */
    @Test
    public void testGetFireSensor() {
        System.out.println("getFireSensor");
        Home instance = new Home();
        FireSensor expResult = new FireSensor(new ArrayList<Floor>());
        instance.setFireSensor(expResult);
        FireSensor result = instance.getFireSensor();
        assertEquals(expResult, result);
    }

    /**
     * Test of setFireSensor method, of class Home.
     */
    @Test
    public void testSetFireSensor() {
        System.out.println("setFireSensor");
        FireSensor fireSensor = new FireSensor(new ArrayList<Floor>());
        Home instance = new Home();
        instance.setFireSensor(fireSensor);
        assertEquals(fireSensor, instance.getFireSensor());
    }

    /**
     * Test of getWeatherSensor method, of class Home.
     */
    @Test
    public void testGetWeatherSensor() {
        System.out.println("getWeatherSensor");
        Home instance = new Home();
        WeatherSensor expResult = new WeatherSensor();
        instance.setWeatherSensor(expResult);
        WeatherSensor result = instance.getWeatherSensor();
        assertEquals(expResult, result);
    }

    /**
     * Test of setWeatherSensor method, of class Home.
     */
    @Test
    public void testSetWeatherSensor() {
        System.out.println("setWeatherSensor");
        WeatherSensor weatherSensor = new WeatherSensor();
        Home instance = new Home();
        instance.setWeatherSensor(weatherSensor);
        assertEquals(weatherSensor, instance.getWeatherSensor());
    }

    /**
     * Test of getAur method, of class Home.
     */
    @Test
    public void testGetAur() {
        System.out.println("getAur");
        Home instance = new Home();
        ActivityAndUsageReporter expResult = new ActivityAndUsageReporter();
        instance.setAur(expResult);
        ActivityAndUsageReporter result = instance.getAur();
        assertEquals(expResult, result);
    }

    /**
     * Test of setAur method, of class Home.
     */
    @Test
    public void testSetAur() {
        System.out.println("setAur");
        ActivityAndUsageReporter aur = new ActivityAndUsageReporter();
        Home instance = new Home();
        instance.setAur(aur);
        assertEquals(aur, instance.getAur());
    }

    /**
     * Test of getConcreteRoom method, of class Home.
     */
    @Test
    public void testGetConcreteRoom() {
        System.out.println("getConcreteRoom");
        String roomType = "Kitchen";
        Home instance = new Home();
        Room expResult = new Room();
        expResult.setRoomType("Kitchen");
        ArrayList<Room> rooms = new ArrayList<Room>();
        rooms.add(expResult);
        Floor floor = new Floor();
        floor.setRooms(rooms);
        ArrayList<Floor> floors = new ArrayList<Floor>();
        floors.add(floor);
        instance.setFloors(floors);
        Room result = instance.getConcreteRoom(roomType);
        assertEquals(expResult, result);
    }
    
}

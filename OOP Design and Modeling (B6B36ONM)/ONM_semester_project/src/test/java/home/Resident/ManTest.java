package project.home.Resident;

import project.Reporter.ActivityAndUsageReporter;
import project.Time.WorldState;
import project.home.Builders.HomeBuilder;
import project.home.Builders.HomeDirector;
import project.home.Builders.SmallHomeBuilder;
import project.home.Device.Device;
import project.home.Device.DeviceOff;
import project.home.Device.DeviceOn;
import project.home.Device.Television;
import project.home.Home;
import project.home.SportResource.Bike;
import project.home.SportResource.SportResource;
import project.home.room.Room;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 *
 * @author �t�p�n Otta
 */
public class ManTest {
    
    public ManTest() {
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
     * Test of getManType method, of class Man.
     */
    @Test
    public void testGetManType() {
        System.out.println("getManType");
        Home h = new Home();
        Man instance = new Father(h);
        String expResult = "Father";
        String result = instance.getManType();
        assertEquals(expResult, result);
    }

    /**
     * Test of setLocation method, of class Man.
     */
    @Test
    public void testSetLocation() {
        Home h = new Home();
        System.out.println("setLocation");
        Room room = new Room();
        Man instance = new Father(h);
        instance.setLocation(room);

    }

    /**
     * Test of getLocation method, of class Man.
     */
    @Test
    public void testGetLocation() {
        System.out.println("getLocation");
        Home h = new Home();
        Man instance = new Father(h);
        Room expResult = null;
        Room result = instance.getLocation();
        assertEquals(expResult, result);
    }


    /**
     * Test of turnOnDevice method, of class Man.
     */
    @Test
    public void testTurnOnDevice() {
        System.out.println("turnOnDevice");
        Device device = new Television(new Room());
        Man instance = new Father(new Home());
        instance.turnOnDevice(device);
        assertEquals(device.getState() instanceof DeviceOn, true);
    }

    /**
     * Test of turnOffDevice method, of class Man.
     */
    @Test
    public void testTurnOffDevice() {
        System.out.println("turnOffDevice");
        Device device = new Television(new Room());
        Man instance = new Father(new Home());
        instance.turnOffDevice(device);
        assertEquals(device.getState() instanceof DeviceOff, true);
    }

    /**
     * Test of goSport method, of class Man.
     */
    @Test
    public void testGoSport() {
        System.out.println("goSport");
        Home h = new Home();
        h.setAur(new ActivityAndUsageReporter());
        h.setWorldState(new WorldState(0,0,0,0,0));
        ArrayList<SportResource> sr = new ArrayList<SportResource>();
        sr.add(new Bike());
        h.setSportResources(sr);
        Man instance = new Father(h);
        boolean expResult = true;
        boolean result = instance.goSport();
        assertEquals(expResult, result);

    }

    /**
     * Test of changeLocation method, of class Man.
     */
    @Test
    public void testChangeLocation() {
        System.out.println("changeLocation");
        HomeBuilder hb = new SmallHomeBuilder();
        HomeDirector hd = new HomeDirector(hb);
        Home h = hd.buildHome();
        h.setAur(new ActivityAndUsageReporter());
        h.setWorldState(new WorldState(0,0,0,0,0));
        Man instance = new Father(h);
        instance.setLocation(new Room());
        instance.changeLocation();

    }

    /**
     * Test of useDevice method, of class Man.
     */
    @Test
    public void testUseDevice() {
        System.out.println("useDevice");
        HomeBuilder hb = new SmallHomeBuilder();
        HomeDirector hd = new HomeDirector(hb);
        Home h = hd.buildHome();
        h.setAur(new ActivityAndUsageReporter());
        h.setWorldState(new WorldState(0,0,0,0,0));
        Man instance = new Father(h);
        instance.setLocation(h.getFloors().get(0).getRooms().get(0));
        instance.useDevice();
        assertNotEquals(instance.myDevice, null);
    }

    /**
     * Test of doSomething method, of class Man.
     */
    @Test
    public void testDoSomething() {
        System.out.println("doSomething");
        HomeBuilder hb = new SmallHomeBuilder();
        HomeDirector hd = new HomeDirector(hb);
        Home h = hd.buildHome();
        h.setAur(new ActivityAndUsageReporter());
        h.setWorldState(new WorldState(0,0,0,0,0));
        Man instance = new Father(h);
        instance.doSomething();
    }

    /**
     * Test of feedAnimal method, of class Man.
     */
    @Test
    public void testFeedAnimal() {
        System.out.println("feedAnimal");
        HomeBuilder hb = new SmallHomeBuilder();
        HomeDirector hd = new HomeDirector(hb);
        Home h = hd.buildHome();
        h.setAur(new ActivityAndUsageReporter());
        h.setWorldState(new WorldState(0,0,0,0,0));
        Animal animal = new Cat(h);
        Man instance = new Father(h);
        instance.feedAnimal(animal);
    }

    /**
     * Test of walkAnimal method, of class Man.
     */
    @Test
    public void testWalkAnimal() {
        System.out.println("walkAnimal");
        HomeBuilder hb = new SmallHomeBuilder();
        HomeDirector hd = new HomeDirector(hb);
        Home h = hd.buildHome();
        h.setAur(new ActivityAndUsageReporter());
        h.setWorldState(new WorldState(0,0,0,0,0));
        Animal animal = new Cat(h);
        Man instance = new Father(h);
        instance.walkAnimal(animal);
    }



    /**
     * Test of fireRun method, of class Man.
     */
    @Test
    public void testFireRun() {
        System.out.println("fireRun");
        Man instance = new Father(new Home());
        instance.fireRun();
    }

    /**
     * Test of fireCallFireman method, of class Man.
     */
    @Test
    public void testFireCallFireman() {
        System.out.println("fireCallFireman");
        Man instance = new Father(new Home());
        instance.fireCallFireman();
    }


    /**
     * Test of getHome method, of class Man.
     */
    @Test
    public void testGetHome() {
        System.out.println("getHome");
        HomeBuilder hb = new SmallHomeBuilder();
        HomeDirector hd = new HomeDirector(hb);
        Home h = hd.buildHome();
        h.setAur(new ActivityAndUsageReporter());
        h.setWorldState(new WorldState(0,0,0,0,0));
        Man instance = new Father(h);
        Home result = instance.getHome();
        assertEquals(h, result);

    }
    
}

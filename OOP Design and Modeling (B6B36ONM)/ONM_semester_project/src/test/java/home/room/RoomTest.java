package project.home.room;

import project.home.Device.AudioPlayer;
import project.home.Device.Device;
import project.home.Window.Window;
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
public class RoomTest {
    
    public RoomTest() {
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
     * Test of setRoomType method, of class Room.
     */
    @Test
    public void testSetRoomType() {
        System.out.println("setRoomType");
        String roomType = "Kitchen";
        Room instance = new Room();
        instance.setRoomType(roomType);
        assertEquals(instance.getRoomType(), roomType);
    }

    /**
     * Test of setDevices method, of class Room.
     */
    @Test
    public void testSetDevices() {
        System.out.println("setDevices");
        ArrayList<Device> devices = new ArrayList<Device>();
        Room instance = new Room();
        devices.add(new AudioPlayer(instance));
        instance.setDevices(devices);
        assertEquals(devices, instance.getDevices());
    }

    /**
     * Test of setWindows method, of class Room.
     */
    @Test
    public void testSetWindows() {
        System.out.println("setWindows");
        ArrayList<Window> windows = new ArrayList<Window>();
        Room instance = new Room();
        windows.add(new Window());
        instance.setWindows(windows);
        assertEquals(windows, instance.getWindows());
    }

    /**
     * Test of getRoomType method, of class Room.
     */
    @Test
    public void testGetRoomType() {
        System.out.println("getRoomType");
        Room instance = new Room();
        instance.setRoomType("Kitchen");
        String expResult = "Kitchen";
        String result = instance.getRoomType();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDevices method, of class Room.
     */
    @Test
    public void testGetDevices() {
        System.out.println("getDevices");
        Room instance = new Room();
        ArrayList<Device> expResult = new ArrayList<Device>();
        expResult.add(new AudioPlayer(instance));
        instance.setDevices(expResult);
        ArrayList<Device> result = instance.getDevices();
        assertEquals(expResult, result);
    }

    /**
     * Test of getWindows method, of class Room.
     */
    @Test
    public void testGetWindows() {
        System.out.println("getWindows");
        Room instance = new Room();
        ArrayList<Window> expResult = new ArrayList<Window>();
        instance.setWindows(expResult);
        ArrayList<Window> result = instance.getWindows();
        assertEquals(expResult, result);
    }
    
}

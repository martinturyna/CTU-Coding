package project.home.Resident;

import project.Reporter.ActivityAndUsageReporter;
import project.Time.WorldState;
import project.home.Builders.HomeDirector;
import project.home.Builders.SmallHomeBuilder;
import project.home.Home;
import project.home.Resident.AnimalState.AnimalState;
import project.home.Resident.AnimalState.HungryState;
import project.home.Resident.AnimalState.NormalState;
import project.home.room.Room;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Otta
 */
public class AnimalTest {
    private Home home;
    
    public AnimalTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.home = new Home();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getLocation method, of class Animal.
     */
    @Test
    public void testGetLocation() {
        System.out.println("getLocation");
        Animal instance = new Cat(home);
        Room expResult = new Room();
        instance.setLocation(expResult);
        Room result = instance.getLocation();
        assertEquals(expResult, result);
    }

    /**
     * Test of isHungry method, of class Animal.
     */
    @Test
    public void testIsHungry() {
        System.out.println("isHungry");
        Animal instance = new Cat(home);
        boolean expResult = false;
        boolean result = instance.isHungry();
        assertEquals(expResult, result);
    }

    /**
     * Test of setHungry method, of class Animal.
     */
    @Test
    public void testSetHungry() {
        System.out.println("setHungry");
        boolean hungry = true;
        Animal instance = new Cat(home);
        instance.setHungry(hungry);
    }

    /**
     * Test of isNeedWalk method, of class Animal.
     */
    @Test
    public void testIsNeedWalk() {
        System.out.println("isNeedWalk");
        Animal instance = new Cat(home);
        boolean expResult = false;
        boolean result = instance.isNeedWalk();
        assertEquals(expResult, result);
    }

    /**
     * Test of setNeedWalk method, of class Animal.
     */
    @Test
    public void testSetNeedWalk() {
        System.out.println("setNeedWalk");
        boolean needWalk = false;
        Animal instance = new Cat(home);
        instance.setNeedWalk(needWalk);
    }

    /**
     * Test of Feed method, of class Animal.
     */
    @Test
    public void testFeed() {
        System.out.println("Feed");
        Animal instance = new Cat(home);
        instance.Feed();
        assertEquals(false,instance.isHungry());
    }

    /**
     * Test of goForWalk method, of class Animal.
     */
    @Test
    public void testGoForWalk() {
        System.out.println("goForWalk");
        Animal instance = new Cat(home);
        instance.goForWalk();
        assertEquals(false,instance.isNeedWalk());
    }

    /**
     * Test of setLocation method, of class Animal.
     */
    @Test
    public void testSetLocation() {
        System.out.println("setLocation");
        Room location = new Room();
        Animal instance = new Cat(home);
        instance.setLocation(location);
    }

    /**
     * Test of updateNeeds method, of class Animal.
     */
    @Test
    public void testUpdateNeeds() {
        System.out.println("updateNeeds");
        Animal instance = new Cat(home);
        instance.updateNeeds();
        assertEquals(95, instance.hunger);
        assertEquals(92, instance.walkNeed);
    }

    /**
     * Test of setState method, of class Animal.
     */
    @Test
    public void testSetState() {
        System.out.println("setState");
        Animal instance = new Cat(home);
        AnimalState state = new HungryState(instance);
        instance.setState(state);
    }

    /**
     * Test of getHome method, of class Animal.
     */
    @Test
    public void testGetHome() {
        System.out.println("getHome");
        Animal instance = new Cat(home);
        Home expResult = home;
        Home result = instance.getHome();
        assertEquals(expResult, result);
    }

    /**
     * Test of isOccupied method, of class Animal.
     */
    @Test
    public void testIsOccupied() {
        System.out.println("isOccupied");
        Animal instance = new Cat(home);
        boolean expResult = false;
        boolean result = instance.isOccupied();
        assertEquals(expResult, result);
    }

    /**
     * Test of setOccupied method, of class Animal.
     */
    @Test
    public void testSetOccupied() {
        System.out.println("setOccupied");
        boolean occupied = true;
        Animal instance = new Cat(home);
        instance.setOccupied(occupied);
    }

    /**
     * Test of doSomething method, of class Animal.
     */
    @Test
    public void testDoSomething() {
        System.out.println("doSomething");
        HomeDirector hm = new HomeDirector(new SmallHomeBuilder());
        home = hm.buildHome();
        home.setAur(new ActivityAndUsageReporter());
        home.setWorldState(new WorldState(2016, 1 , 3, 0 , 100));
        Animal instance = new Cat(home);
        instance.setLocation(home.getFloors().get(0).getRooms().get(0));
        instance.setState(new NormalState(instance));
        instance.doSomething();
        assertEquals(95, instance.hunger);
        assertEquals(92, instance.walkNeed);
    }

    public class AnimalImpl extends Animal {

        public AnimalImpl() {
            super(null);
        }

        public String getAnimalType() {
            return "";
        }
    }
    
}

package project.home.Facilities;

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
public class FacilityTest {
    
    public FacilityTest() {
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
     * Test of setState method, of class Facility.
     */
    @Test
    public void testSetState() {
        System.out.println("setState");
        Facility instance = new Heating();
        FacilityState state = new FacilityOn(instance);
        instance.setState(state);
        assertEquals(instance.getState(), state);
    }

    /**
     * Test of turnOn method, of class Facility.
     */
    @Test
    public void testTurnOn() {
        System.out.println("turnOn");
        Facility instance = new Heating();
        FacilityState state = new FacilityOff(instance);
        instance.setState(state);
        instance.turnOn();
        assert(instance.getState() instanceof FacilityOn);
    }

    /**
     * Test of turnOff method, of class Facility.
     */
    @Test
    public void testTurnOff() {
        System.out.println("turnOff");
        Facility instance = new Heating();
        FacilityState state = new FacilityOn(instance);
        instance.setState(state);
        instance.turnOff();
        assert(instance.getState() instanceof FacilityOff);
    }
    
}

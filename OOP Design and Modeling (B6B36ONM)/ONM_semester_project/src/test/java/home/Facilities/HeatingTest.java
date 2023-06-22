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
public class HeatingTest {
    
    public HeatingTest() {
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
     * Test of getOnEnergyUse method, of class Heating.
     */
    @Test
    public void testGetOnEnergyUse() {
        System.out.println("getOnEnergyUse");
        Heating instance = new Heating();
        int expResult = 90;
        int result = instance.getOnEnergyUse();
        assertEquals(expResult, result);
    }

    /**
     * Test of getOffEnergyUse method, of class Heating.
     */
    @Test
    public void testGetOffEnergyUse() {
        System.out.println("getOffEnergyUse");
        Heating instance = new Heating();
        int expResult = 0;
        int result = instance.getOffEnergyUse();
    }

    /**
     * Test of getDeviceType method, of class Heating.
     */
    @Test
    public void testGetDeviceType() {
        System.out.println("getDeviceType");
        Heating instance = new Heating();
        String expResult = "Heating";
        String result = instance.getDeviceType();
        assertEquals(expResult, result);
    }
    
}

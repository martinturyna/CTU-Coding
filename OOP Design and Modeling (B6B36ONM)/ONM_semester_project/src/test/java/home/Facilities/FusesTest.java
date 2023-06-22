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
public class FusesTest {
    
    public FusesTest() {
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
     * Test of getOnEnergyUse method, of class Fuses.
     */
    @Test
    public void testGetOnEnergyUse() {
        System.out.println("getOnEnergyUse");
        Fuses instance = new Fuses();
        int expResult = 0;
        int result = instance.getOnEnergyUse();
        assertEquals(expResult, result);
    }

    /**
     * Test of getOffEnergyUse method, of class Fuses.
     */
    @Test
    public void testGetOffEnergyUse() {
        System.out.println("getOffEnergyUse");
        Fuses instance = new Fuses();
        int expResult = 0;
        int result = instance.getOffEnergyUse();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDeviceType method, of class Fuses.
     */
    @Test
    public void testGetDeviceType() {
        System.out.println("getDeviceType");
        Fuses instance = new Fuses();
        String expResult = "Fuses";
        String result = instance.getDeviceType();
        assertEquals(expResult, result);
    }
    
}

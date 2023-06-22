package project.home.SportResource;

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
public class SportResourceTest {
    
    public SportResourceTest() {
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
     * Test of goSport method, of class SportResource.
     */
    @Test
    public void testGoSport() {
        System.out.println("goSport");
        SportResource instance = new Ball();
        instance.setState(new SportResourceReady(instance));
        boolean expResult = true;
        boolean result = instance.goSport();
        assertEquals(expResult, result);
    }

    /**
     * Test of goBackHome method, of class SportResource.
     */
    @Test
    public void testGoBackHome() {
        System.out.println("goBackHome");
        SportResource instance = new Ball();
        instance.setState(new SportResourceInUse(instance));
        instance.goBackHome();
        assert(instance.getState() instanceof SportResourceReady);
    }

    /**
     * Test of getState method, of class SportResource.
     */
    @Test
    public void testGetState() {
        System.out.println("getState");
        SportResource instance = new Ball();
        SportResourceState expResult = new SportResourceReady(instance);
        instance.setState(expResult);
        SportResourceState result = instance.getState();
        assertEquals(expResult, result);
    }

    /**
     * Test of setState method, of class SportResource.
     */
    @Test
    public void testSetState() {
        System.out.println("setState");
        SportResource instance = new Ball();
        SportResourceState state = new SportResourceReady(instance) ;
        instance.setState(state);
        assertEquals(instance.getState(), state);
    }

    /**
     * Test of getSportResourceType method, of class SportResource.
     */
    @Test
    public void testGetSportResourceType() {
        System.out.println("getSportResourceType");
        SportResource instance = new Ball();
        String expResult = "Ball";
        String result = instance.getSportResourceType();
        assertEquals(expResult, result);
    }
    
}

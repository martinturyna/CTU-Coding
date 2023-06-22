package project.home.Window.WindowBlind;

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
public class WindowBlindTest {
    
    public WindowBlindTest() {
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
     * Test of open method, of class WindowBlind.
     */
    @Test
    public void testOpen() {
        System.out.println("open");
        WindowBlind instance = new WindowBlind();
        instance.setState(new WindowBlindClose(instance));
        instance.open();
        assert(instance.getState() instanceof WindowBlindOpen);
    }

    /**
     * Test of close method, of class WindowBlind.
     */
    @Test
    public void testClose() {
        System.out.println("close");
        WindowBlind instance = new WindowBlind();
        instance.setState(new WindowBlindOpen(instance));
        instance.close();
        assert(instance.getState() instanceof WindowBlindClose);
    }

    /**
     * Test of getState method, of class WindowBlind.
     */
    @Test
    public void testGetState() {
        System.out.println("getState");
        WindowBlind instance = new WindowBlind();
        WindowBlindState expResult = new WindowBlindClose(instance);
        instance.setState(expResult);
        WindowBlindState result = instance.getState();
        assertEquals(expResult, result);
    }

    /**
     * Test of setState method, of class WindowBlind.
     */
    @Test
    public void testSetState() {
        System.out.println("setState");
        WindowBlind instance = new WindowBlind();
        WindowBlindState state = new WindowBlindOpen(instance);
        instance.setState(state);
        assertEquals(instance.getState(), state);
    }
    
}

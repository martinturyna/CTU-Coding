package project.home.Window;

import project.home.Window.WindowBlind.WindowBlind;
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
public class WindowTest {
    
    public WindowTest() {
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
     * Test of open method, of class Window.
     */
    @Test
    public void testOpen() {
        System.out.println("open");
        Window instance = new Window();
        instance.setState(new WindowClosed(instance));
        instance.open();
        assert(instance.getState() instanceof WindowOpen);
    }

    /**
     * Test of close method, of class Window.
     */
    @Test
    public void testClose() {
        System.out.println("close");
        Window instance = new Window();
        instance.setState(new WindowOpen(instance));
        instance.close();
        assert(instance.getState() instanceof WindowClosed);
    }

    /**
     * Test of getState method, of class Window.
     */
    @Test
    public void testGetState() {
        System.out.println("getState");
        Window instance = new Window();
        WindowState expResult = new WindowOpen(instance);
        instance.setState(expResult);
        WindowState result = instance.getState();
        assertEquals(expResult, result);
    }

    /**
     * Test of getWindowBlind method, of class Window.
     */
    @Test
    public void testGetWindowBlind() {
        System.out.println("getWindowBlind");
        Window instance = new Window();
        WindowBlind expResult = new WindowBlind();
        instance.setWindowBlind(expResult);
        WindowBlind result = instance.getWindowBlind();
        assertEquals(expResult, result);
    }

    /**
     * Test of setWindowBlind method, of class Window.
     */
    @Test
    public void testSetWindowBlind() {
        System.out.println("setWindowBlind");
        WindowBlind windowBlind = new WindowBlind();
        Window instance = new Window();
        instance.setWindowBlind(windowBlind);
        assertEquals(windowBlind, instance.getWindowBlind());
    }

    /**
     * Test of setState method, of class Window.
     */
    @Test
    public void testSetState() {
        System.out.println("setState");
        Window instance = new Window();
        WindowState state = new WindowOpen(instance);
        instance.setState(state);
        assertEquals(state, instance.getState());
    }
    
}

package project.home.Resident;

import project.home.Home;
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
public class DogTest {
    
    public DogTest() {
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
     * Test of getAnimalType method, of class Dog.
     */
    @Test
    public void testGetAnimalType() {
        System.out.println("getAnimalType");
        Home home = new Home();
        Dog instance = new Dog(home);
        String expResult = "Dog";
        String result = instance.getAnimalType();
        assertEquals(expResult, result);
    }
    
}

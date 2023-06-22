/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class CatTest {
    
    public CatTest() {
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
     * Test of getAnimalType method, of class Cat.
     */
    @Test
    public void testGetAnimalType() {
        System.out.println("getAnimalType");
        Home home = new Home();
        Cat instance = new Cat(home);
        String expResult = "Cat";
        String result = instance.getAnimalType();
        assertEquals(expResult, result);
    }
    
}

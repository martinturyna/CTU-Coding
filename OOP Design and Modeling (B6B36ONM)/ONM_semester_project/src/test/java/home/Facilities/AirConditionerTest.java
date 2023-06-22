/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class AirConditionerTest {
    
    public AirConditionerTest() {
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
     * Test of getOnEnergyUse method, of class AirConditioner.
     */
    @Test
    public void testGetOnEnergyUse() {
        System.out.println("getOnEnergyUse");
        AirConditioner instance = new AirConditioner();
        int expResult = 85;
        int result = instance.getOnEnergyUse();
        assertEquals(expResult, result);
    }

    /**
     * Test of getOffEnergyUse method, of class AirConditioner.
     */
    @Test
    public void testGetOffEnergyUse() {
        System.out.println("getOffEnergyUse");
        AirConditioner instance = new AirConditioner();
        int expResult = 0;
        int result = instance.getOffEnergyUse();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDeviceType method, of class AirConditioner.
     */
    @Test
    public void testGetDeviceType() {
        System.out.println("getDeviceType");
        AirConditioner instance = new AirConditioner();
        String expResult = "AirConditioner";
        String result = instance.getDeviceType();
        assertEquals(expResult, result);
    }
    
}

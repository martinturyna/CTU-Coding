package project.home.Device;

import project.home.room.Room;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Csaba on 12/26/2017.
 */
public class DeviceTest {

    @Test
    public void isIsWorking() {
        Device d = new Television(new Room());
        assertEquals(d.isIsWorking(), true);
        d.breakDevice();
        assertEquals(d.isIsWorking(), false);
    }

    @Test
    public void breakDevice() {
        Device d = new VacuumCleaner(new Room());
        d.breakDevice();
        assertEquals(d.isIsWorking(), false);
    }

    @Test
    public void turnOn() {
        Device d = new AudioPlayer(new Room());
        d.turnOn();
        assertEquals(d.getState() instanceof DeviceOn, true);
    }

    @Test
    public void turnOff() {
        Device d = new AudioPlayer(new Room());
        d.turnOff();
        assertEquals(d.getState() instanceof DeviceOff, true);
    }

    @Test
    public void turnIdle() throws Exception {
        Device d = new AudioPlayer(new Room());
        d.turnIdle();
        assertEquals(d.getState() instanceof DeviceIdle, true);
    }

    @Test
    public void updateLifetime(){
        Device d = new Television(new Room());
        d.updateLifetime();
        assertEquals(d.getLifetime(), 100);
        d.turnOn();
        d.updateLifetime();
        assertEquals(d.getLifetime(), 95);
    }


    @Test
    public void getDeviceType(){
        Device tv = new Television(new Room());
        Device mw = new Microwave(new Room());
        assertEquals(tv.getDeviceType(), "Television");
        assertEquals(mw.getDeviceType(), "Microwave");
    }

    @Test
    public void getState(){
        Device tv = new Television(new Room());
        assertEquals(tv.getState() instanceof DeviceOff, true);
        tv.turnOn();
        assertEquals(tv.getState() instanceof DeviceOn, true);
    }

    @Test
    public void repair() {
        Device tv = new Television(new Room());
        tv.breakDevice();
        tv.repair();
        assertEquals(tv.isIsWorking(), true);
    }

}
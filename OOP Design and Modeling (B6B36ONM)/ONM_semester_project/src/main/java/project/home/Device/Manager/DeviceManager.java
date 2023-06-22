package project.home.Device.Manager;

import project.Reporter.EventReporter;
import project.Time.WorldState;
import project.home.Device.Device;
import project.home.Device.Sensor.FireSensor;
import project.home.Device.Sensor.WeatherSensor;
import project.home.Facilities.Facility;
import project.home.Home;
import project.home.Visitor.AdjustToTemperatureVisitor;
import project.home.Visitor.HandleWeatherVisitor;
import project.home.Window.Window;

import java.util.ArrayList;


/**
 * DeviceManager class handle and manage all device actions.
 * Reacts on Sensors or Weather influence.
 */

public class DeviceManager {

    private FireSensor fireSensor;
    private WeatherSensor weatherSensor;
    private ArrayList<Device> devices;
    private ArrayList<Window> windows;
    private ArrayList<Facility> facilities;
    private WorldState worldState;
    private ResidentManager residentManager;
    private EventReporter eventReporter;
    private HandleWeatherVisitor hwv;
    private AdjustToTemperatureVisitor atv;

    public DeviceManager(Home home, WorldState worldState, ResidentManager residentManager) {
        this.devices = home.getDevices();
        this.windows = home.getWindows();
        this.facilities = home.getFacilities();
        this.fireSensor = home.getFireSensor();
        this.weatherSensor = home.getWeatherSensor();
        this.worldState = worldState;
        this.residentManager = residentManager;
        this.eventReporter = new EventReporter(worldState);
        hwv = new HandleWeatherVisitor(windows, devices, eventReporter);
        atv = new AdjustToTemperatureVisitor(worldState, windows);
    }

    /**
     * project.Main method that is used in project.Simulation to update all devices.
     * Firstly check sensors and adjust the project.home to temperature.
     * Than update all devices life time and check if the device is broken.
     * In that case is called method from ResidentManager to add broken device
     * into queue of devices to be repaired.
     */
    public void update() {
        this.sensorUsage();
        this.adjustToTemperature();
        for (Device d : devices) {
            if (d.isIsWorking()) {
                d.updateLifetime();
                if (!d.isIsWorking()) {
                    residentManager.addToBrokenDevices(d);
                    System.out.println(d.getDeviceType());
                    eventReporter.brokenDeviceReport(d);
                }
            }
        }
    }

    /**
     * doWeatherEventActions check if there is any pending generated event
     * in WorldState. Find out the event type and do necessary actions as
     * close or open Windows. If there is lightning during the rain, randomly
     * decide if fuses will go off or not.
     */
    private void doWeatherEventActions() {
        
        worldState.getWeatherEvent().accept(hwv);
        
        /*
        if (worldState.getWeatherEvent() instanceof WindEvent) {
            for (Window w : windows) {
                w.close();
            }
        }
        else if (worldState.getWeatherEvent() instanceof SnowstormEvent) {
            for (Window w : windows) {
                w.close();
            }
        }
        else if (worldState.getWeatherEvent() instanceof RainEvent) {
            if (((RainEvent) worldState.getWeatherEvent()).isLightning()) {
                if (worldState.getWeatherEvent().getStrength() > 80) {
                    eventReporter.fusesReport();
                    for (Device d : devices) {
                        d.turnOff();
                    }
                }
            }
            for (Window w : windows) {
                w.close();
            }
        }*/
    }

    /**
     * sensorUsage is a short method for update sensors.
     * fireSensor can set up fire with very small probability.
     * ResidentManager is then notified about it.
     * Find out if weatherSensor is notified by Observable project.God.
     */
    public void sensorUsage() {
        if (fireSensor.generateFire()) {
            residentManager.setFire(true);
            eventReporter.fireReport(fireSensor);
        }
        else if (weatherSensor.isUpdated()) {
            this.doWeatherEventActions();
            eventReporter.weatherReport(worldState.getWeatherEvent());
        }
    }

    /**
     * adjustToTemperature check the temperature every cycle to do actions
     * connected with it.
     * Temperature > 20 turn AirConditioner on and close all Windows
     * Temperature < 10 turn Heating on
     * Temperature in <10; 20> turn AirCond and Heating off
     */
    public void adjustToTemperature() {
        for(Facility f : facilities){
            f.accept(atv);
        }
        /*
        if (worldState.getTemperature() > 20) {
            for (Facility f : facilities) {
                if (f instanceof AirConditioner) {
                    f.turnOn();
                    for (Window w : windows) {
                        w.close();
                    }
                }
            }
        }
        else if (worldState.getTemperature() < 10) {
            for (Facility f : facilities) {
                if (f instanceof Heating) {
                    f.turnOn();
                }
            }
        }
        else {
            for (Facility f : facilities) {
                if (f instanceof AirConditioner) {
                    f.turnOff();
                }
                else if (f instanceof Heating) {
                    f.turnOff();
                }
            }
        }
        */
    }
}

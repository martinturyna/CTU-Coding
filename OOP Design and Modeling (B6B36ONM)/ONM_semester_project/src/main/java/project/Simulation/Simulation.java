package project.Simulation;

import project.Reporter.ActivityAndUsageReporter;
import project.Reporter.HouseConfigurationReporter;
import project.home.Builders.*;
import project.home.Device.Manager.DeviceManager;
import project.home.Home;

import project.God.God;
import project.Reporter.ConsumptionReporter;
import project.Time.WorldState;
import project.home.Builders.LargeHomeBuilder;
import project.home.Device.Manager.ResidentManager;

/**
 * Class where the build is called and main cycle located.
 * Carry all the final important information such a Home,
 * Reporters, WeatherDuration, project.God, WorldState and Managers.
 */

public class Simulation {
    private int weatherDuration;
    private boolean weatherReady;
    private WorldState worldState;
    private final God god;
    private ResidentManager residentManager;
    private DeviceManager deviceManager;
    private Home home;
    private ConsumptionReporter consumptionReporter;

    public Simulation(int year, int month, int day, int hour, int duration) {
        this.weatherDuration = 0;
        this.weatherReady = true;
        this.worldState = new WorldState(year, month, day, hour, duration);
        this.god = God.getInstance(worldState);
        this.build(worldState);
        this.god.attach(home.getWeatherSensor());
        this.residentManager = new ResidentManager(home.getPeople(), home.getAnimals());
        this.consumptionReporter = new ConsumptionReporter(home.getDevices(), this.worldState);
        this.deviceManager = new DeviceManager(home, worldState, residentManager);
    }

    /**
     * Build method calls the builder and builds the house.
     * @param worldState
     */
    public void build(WorldState worldState) {
        HomeBuilder hb = new LargeHomeBuilder();
        HomeDirector hd = new HomeDirector(hb);
        Home h = hd.buildHome();
        ActivityAndUsageReporter aur = new ActivityAndUsageReporter();
        h.setAur(aur);
        h.setWorldState(worldState);
        HouseConfigurationReporter hcr = new HouseConfigurationReporter(h);
        hcr.createReport();
        this.home = h;
    }


    /**
     * project.Main cycle of the project.Simulation. All updates should be placed here in
     * particular order.
     */
    public void run() {
        while(true) {
            if (!worldState.nextHour()) {
                break;
            }
            this.generateWeather();
            deviceManager.update();
            residentManager.updateAnimals();
            residentManager.updatePeople();
            consumptionReporter.consumptionReport();
        }
        consumptionReporter.finalReport();
    }

    /**
     * generateWeather method helps to manage the weather. Keeps the information
     * about weatherDuration a generate a new weather only if the previous one ended.
     */
    private void generateWeather() {
        if (weatherDuration == 0 && !weatherReady) {
            weatherReady = true;
            worldState.setTemperature(worldState.getCurrentSeason().getAvTemperature());
        }
        if (weatherDuration > 0) {
            weatherDuration--;
        }
        if (weatherReady) {
            god.GenerateWeather();
            if (god.getEvent() != null) {
                worldState.setTemperature(god.getEvent().getTemperature());
                worldState.setWeatherEvent(god.getEvent());
                weatherDuration = god.getEvent().getDuration();
                weatherReady = false;
            }
        }
    }
}

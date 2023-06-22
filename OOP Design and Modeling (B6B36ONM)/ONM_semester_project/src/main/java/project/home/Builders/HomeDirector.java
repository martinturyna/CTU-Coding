package project.home.Builders;

import project.home.Home;

/**
 * HomeDirector builds the desired project.home, which is defined in the HomeBuilder
 * that the HomeDirector receives as parameter in it's constructor.
 */
public class HomeDirector {
    HomeBuilder homeBuilder;

    public HomeDirector(HomeBuilder homeBuilder) {
        this.homeBuilder = homeBuilder;
    }

    public Home buildHome(){
        homeBuilder.buildFloors();
        homeBuilder.buildResidents();
        homeBuilder.buildVehicles();
        homeBuilder.buildSportResources();
        homeBuilder.buildWindows();
        homeBuilder.buildDevices();
        homeBuilder.buildFireSensor();
        homeBuilder.buildWeatherSensor();
        homeBuilder.buildFacilities();
        return homeBuilder.getResult();
    }
}

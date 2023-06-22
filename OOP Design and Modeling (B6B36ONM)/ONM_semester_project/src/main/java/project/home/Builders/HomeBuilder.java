package project.home.Builders;


import project.home.Home;

public interface HomeBuilder {


    public void buildFloors();

    public void buildVehicles();

    public void buildResidents();

    public void buildSportResources();

    public void buildWindows();

    public void buildDevices();

    public void buildFireSensor();

    public void buildWeatherSensor();

    public void buildFacilities();

    public Home getResult();
}

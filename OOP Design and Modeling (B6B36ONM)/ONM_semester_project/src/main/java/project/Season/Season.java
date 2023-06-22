package project.Season;

import project.WeatherEvent.WeatherEvent;

/**
 * project.Season interface that is implemented in Spring, Summer, Autumn and Winter.
 */

public interface Season {

    /**
     * Here is the real generator method, where the project.WeatherEvent from current season
     * is chose and with some probability is generated.
     * Every season has different WeatherEvents.
     * Autumn: Rain, Wind
     * Winter: Snowstorm, Wind, Cold
     * Spring: Rain, Wind
     * Summer: Rain, Wind, Hot
     * @return project.WeatherEvent, that is managed by a DeviceManager
     */
    public WeatherEvent generateWeatherEvent();

    /**
     * If there is no project.WeatherEvent generated, that could influence temperature,
     * than the AvTemperature is taken. For simple implementation is just one temperature
     * for every single season chose.
     * @return avTemperature
     */
    public int getAvTemperature();


}

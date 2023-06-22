package project.Season;

import project.WeatherEvent.WeatherEvent;
import project.WeatherEvent.RainEvent;
import project.WeatherEvent.WindEvent;
import java.util.Random;

// Spring can generate: Wind, Rain
public class Spring implements Season {

    private int avTemperature = 15;

    public int getAvTemperature() {
        return avTemperature;
    }

    public WeatherEvent generateWeatherEvent() {
        WeatherEvent e = null;
        Random ran = new Random();
        int i = ran.nextInt(30);
        if (i == 0) {
            boolean tmp = ran.nextBoolean();
            if (tmp) {
                e = new RainEvent();
            } else {
                e = new WindEvent();
            }
            return e;
        }
        return null;
    }
}

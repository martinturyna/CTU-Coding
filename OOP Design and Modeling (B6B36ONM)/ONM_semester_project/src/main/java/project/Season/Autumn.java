package project.Season;

import project.WeatherEvent.WeatherEvent;
import project.WeatherEvent.RainEvent;
import project.WeatherEvent.WindEvent;

import java.util.Random;

public class Autumn implements Season {

    private int avTemperature = 10;

    public int getAvTemperature() {
        return avTemperature;
    }

    public WeatherEvent generateWeatherEvent() {
        WeatherEvent e = null;
        Random ran = new Random();
        int i = ran.nextInt(30);
        if (i == 0) { // 1:25 probability
            boolean tmp = ran.nextBoolean();
            if (!tmp) {
                e = new RainEvent();
            }
            else {
                e = new WindEvent();
            }
            return e;
        }
        return null;
    }
}

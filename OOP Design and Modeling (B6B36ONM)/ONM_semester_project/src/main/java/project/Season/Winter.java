package project.Season;

import project.WeatherEvent.WeatherEvent;
import project.WeatherEvent.ColdEvent;
import project.WeatherEvent.WindEvent;
import project.WeatherEvent.SnowstormEvent;
import java.util.Random;

public class Winter implements Season {

    private int avTemperature = -5;
    private int currTemperature;

    public int getAvTemperature() {
        return avTemperature;
    }

    public WeatherEvent generateWeatherEvent() {
        WeatherEvent e = null;
        Random ran = new Random();
        int i = ran.nextInt(30);
        if (i == 0) {
            int tmp = ran.nextInt(3);
            if (tmp == 0) {
                e = new SnowstormEvent();
            } else if (tmp == 1) {
                e = new WindEvent();
            } else {
                e = new ColdEvent();
            }
            return e;
        }
        return null;
    }
}

package project.Season;

import project.WeatherEvent.WeatherEvent;
import project.WeatherEvent.RainEvent;
import project.WeatherEvent.WindEvent;
import project.WeatherEvent.HotEvent;
import java.util.Random;

public class Summer implements Season {

    private int avTemperature = 25;

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
                e = new RainEvent();
            } else if (tmp == 1) {
                e = new WindEvent();
            } else {
                e = new HotEvent();
            }
            return e;
        }
        return null;
    }
}

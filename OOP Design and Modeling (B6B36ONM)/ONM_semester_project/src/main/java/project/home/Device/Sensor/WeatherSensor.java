package project.home.Device.Sensor;

import project.WeatherEvent.WeatherEvent;
import project.home.Observer;

/**
 * WeatherSensor gets information from project.God about Weather change
 * Immediately send information to DeviceManager.
 * WeatherSensor is Observer for Observable project.God.
 */

public class WeatherSensor implements Observer {


    private WeatherEvent e;
    private boolean updated = false;

    public boolean isUpdated() {
        if (updated) {
            updated = false;
            return true;
        }
        return false;
    }

    public void update() {
        updated = true;
    }

}

package project.WeatherEvent;

import project.home.Visitor.WeatherEventVisitor;

/**
 * Abstract class for WeatherEvents. Carry all information about weather.
 * Is Randomly generated during season. Duration is represented in hours.
 * When weather event comes, default temperature is changed. *
 */


public abstract class WeatherEvent {

    private int duration; // Duration represents minutes, <0; 4320> (= max 3 days)
    private int temperature; // <-30; 40> Celsius
    private int strength; // represents damage probability <0; 100>


    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public String getString(){ return ""; }
    
    public abstract void accept(WeatherEventVisitor wv);

}

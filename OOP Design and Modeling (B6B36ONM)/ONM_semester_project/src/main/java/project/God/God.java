package project.God;

import project.Time.WorldState;
import project.WeatherEvent.WeatherEvent;
import project.home.Observable;
import project.home.Observer;
import java.util.ArrayList;

/**
 * Observable Class project.God represents random weather generator.
 *
 * Used patterns: Singleton, Observer & Observable
 */
public class God implements Observable {
    public static God instance = null;
    private WorldState worldState;
    private WeatherEvent event;
    private ArrayList<Observer> observers = new ArrayList<Observer>();

    private God(WorldState worldState) {
        this.worldState = worldState;
    }
    
    public static God getInstance(WorldState worldState){
        if(instance == null){
            instance = new God(worldState);
        }
        return instance;
    }

    public WeatherEvent getEvent() {
        return event;
    }
    
    public void attach(Observer observer) {
        if(!observers.contains(observer)){
            observers.add(observer);
        }
    }

    public void detach(Observer observer) {
        if(observers.contains(observer)){
            observers.remove(observer);
        }
    }

    public void notifyAllObservers() {
        for(Observer o : observers){
            o.update();
        }
    }

    /**
     * GenerateWeather method calls current season, that generate
     * weather event. Observer WeatherSensor is notified than.
     */
    public void GenerateWeather(){
        setEvent(worldState.getCurrentSeason().generateWeatherEvent());
        if (event != null) {
            this.notifyAllObservers();
        }
    }

    public void setEvent(WeatherEvent event) {
        this.event = event;
    }
}

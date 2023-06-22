package project.Time;

import project.WeatherEvent.WeatherEvent;
import project.Season.Season;
import project.Season.Spring;
import project.Season.Winter;
import project.Season.Autumn;
import project.Season.Summer;

public class WorldState {
    private Season currentSeason;
    private Season spring;
    private Season winter;
    private Season autumn;
    private Season summer;
    private WeatherEvent weatherEvent;
    private int temperature;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int simDuration; // Duration of simulation in hours
    private int masterHour;

    public WorldState(int year, int month, int day, int hour, int simDuration) {
        this.setWorldState(year, month, day, hour);
        this.simDuration = simDuration;
        this.masterHour = 0;
    }
    public void setWorldState(int year, int month, int day, int hour) {
        this.setYear(year);
        this.setMonth(month);
        this.setDay(day);
        this.setHour(hour);
        spring = new Spring();
        winter = new Winter();
        autumn = new Autumn();
        summer = new Summer();
        if (this.getMonth() >= 3 && this.getMonth() <= 5) {
            setCurrentSeason(spring);
            this.temperature = currentSeason.getAvTemperature();
        }
        else if (this.getMonth() >= 6 && this.getMonth() <= 8) {
            setCurrentSeason(summer);
            this.temperature = currentSeason.getAvTemperature();
        }
        else if (this.getMonth() >= 9 && this.getMonth() <= 11) {
            setCurrentSeason(autumn);
            this.temperature = currentSeason.getAvTemperature();
        }
        else {
            setCurrentSeason(winter);
            this.temperature = currentSeason.getAvTemperature();
        }
    }

    /**
     * This method moves project.Time hour by hour
     * If Hour == 23, set Hour = 1 ( = next day) etc.
     * For our simulation has every month just 30 days.
     * Return false if simulation is at the end and stop the
     * simulation.
     */
    public boolean nextHour() {
        if (this.masterHour == this.simDuration){
            return false;
        }
        if (this.getHour() == 23) {
            this.setHour(0);
            if (this.getDay() == 30) {
                System.out.println("["+ this.getDay()+ "/" + this.getMonth() + "] = Temperature: " + temperature);
                this.setDay(1);
                if (this.getMonth() == 12) {
                    this.setMonth(1);
                    this.setYear(this.getYear()+1);
                }
                else {
                    this.setMonth(this.getMonth() + 1);
                    if (this.getMonth() == 3) {
                        setCurrentSeason(spring);
                        this.temperature = currentSeason.getAvTemperature();
                    }
                    else if (this.getMonth() == 6) {
                        setCurrentSeason(summer);
                        this.temperature = currentSeason.getAvTemperature();
                    }
                    else if (this.getMonth() == 9) {
                        setCurrentSeason(autumn);
                        this.temperature = currentSeason.getAvTemperature();
                    }
                    else if (this.getMonth() == 12) {
                        setCurrentSeason(winter);
                        this.temperature = currentSeason.getAvTemperature();
                    }
                }
            }
            else {
                System.out.println("["+ this.getDay()+ "/" + this.getMonth() + "/" + this.getYear() + "] " +
                        "= Temperature: " + temperature);
                this.setDay(this.getDay() + 1);
            }
        }
        else {
            this.setHour(this.getHour() + 1);
            this.masterHour++;
        }

        return true;
    }
    public int getYear() { return year; }

    public void setYear(int year) { this.year = year; }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setCurrentSeason(Season season) {
        currentSeason = season;
    }

    public void setTemperature(int temperature) { this.temperature = temperature; }

    public int getTemperature() { return this.temperature; }

    public Season getCurrentSeason() {
        return currentSeason;
    }

    public WeatherEvent getWeatherEvent() { return this.weatherEvent; }

    public void setWeatherEvent(WeatherEvent weatherEvent) { this.weatherEvent = weatherEvent; }
}

package project.WeatherEvent;

import project.home.Visitor.WeatherEventVisitor;
import java.util.Random;

public class SnowstormEvent extends WeatherEvent {

    private int snowHeight;
    private String s = "Snowstorm";

    public SnowstormEvent() {
        Random ran = new Random();
        this.setDuration(ran.nextInt(73));
        this.setTemperature(ran.nextInt(16) - 20); // <-20; -5>
        this.setStrength(ran.nextInt(101));
        this.generateSnowHeight(ran.nextInt(101));
    }

    public void generateSnowHeight(int n) {
        /*
        * Different probability of snowfall height
        * - 0.5 for <0; 10> cm
        * - 0.35 for <10; 50> cm
        * - 0.15 for <50; 100> cm
        */
        Random ran = new Random();
        if (n <= 50) {
            setSnowHeight(ran.nextInt(10));
        }
        else if (n <= 85) {
            setSnowHeight(ran.nextInt(40) + 10);
        }
        else {
            setSnowHeight(ran.nextInt(50) + 50);
        }
    }

    public int getSnowHeight() {
        return snowHeight;
    }

    public void setSnowHeight(int snowHeight) {
        this.snowHeight = snowHeight;
    }

    @Override
    public String getString() {
        return s + " with " + getSnowHeight() + "cm height";
    }

    @Override
    public void accept(WeatherEventVisitor wv) {
        wv.visitsnowStormEvent(this);
    }

}

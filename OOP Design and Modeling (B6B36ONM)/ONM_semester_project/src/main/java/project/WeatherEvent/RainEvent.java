package project.WeatherEvent;

import project.home.Visitor.WeatherEventVisitor;
import java.util.Random;

public class RainEvent extends WeatherEvent {

    private boolean isLightning;
    private boolean isRaining;
    private String s = "Rain";

    public RainEvent() {
        Random ran = new Random();
        this.setDuration(ran.nextInt(73));
        this.setTemperature(ran.nextInt(6) + 5); // <5; 10>
        this.setStrength(ran.nextInt(101));
        this.setLightning(ran.nextBoolean());
        this.setRaining(ran.nextBoolean());
    }

    public boolean isLightning() {
        return isLightning;
    }

    public void setLightning(boolean lightning) {
        isLightning = lightning;
    }

    public boolean isRaining() {
        return isRaining;
    }

    public void setRaining(boolean raining) {
        isRaining = raining;
    }

    @Override
    public String getString() {
        if (isLightning) {
            return s + " with lightning";
        }
        return s;
    }

    @Override
    public void accept(WeatherEventVisitor wv) {
        wv.visitRainEvent(this);
    }



}

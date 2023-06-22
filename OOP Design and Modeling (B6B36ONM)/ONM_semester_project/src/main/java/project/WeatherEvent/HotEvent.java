package project.WeatherEvent;

import project.home.Visitor.WeatherEventVisitor;
import java.util.Random;

public class HotEvent extends WeatherEvent {
    private String s = "Hot";

    public HotEvent() {
        Random ran = new Random();
        this.setDuration(ran.nextInt(73));
        this.setTemperature(ran.nextInt(11) + 25); // <25; 35>
        this.setStrength(ran.nextInt(101));
    }
    @Override
    public String getString() {
        return s;
    }

    @Override
    public void accept(WeatherEventVisitor wv) {
        wv.visitHotEvent(this);
    }

}

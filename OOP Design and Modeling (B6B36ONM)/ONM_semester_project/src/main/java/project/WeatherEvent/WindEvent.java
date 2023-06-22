package project.WeatherEvent;

import project.home.Visitor.WeatherEventVisitor;
import java.util.Random;

public class WindEvent extends WeatherEvent {

    private String s = "Wind";

    public WindEvent() {
        Random ran = new Random();
        this.setDuration(ran.nextInt(73));
        this.setTemperature(ran.nextInt(11) + 5); // <5;15>
        this.setStrength(ran.nextInt(101));
    }

    @Override
    public String getString() {
        return s;
    }

    @Override
    public void accept(WeatherEventVisitor wv) {
        wv.visitWindEvent(this);
    }

}

package project.WeatherEvent;

import project.home.Visitor.WeatherEventVisitor;
import java.util.Random;

public class ColdEvent extends WeatherEvent {
    private String s = "Cold";

    public ColdEvent() {
        Random ran = new Random();
        this.setDuration(ran.nextInt(73));
        this.setTemperature(ran.nextInt(11) - 10); // <-20; -10>
        this.setStrength(ran.nextInt(101));
    }
    @Override
    public String getString() {
        return s;
    }

    @Override
    public void accept(WeatherEventVisitor wv) {
        wv.visitColdEvent(this);
    }

}

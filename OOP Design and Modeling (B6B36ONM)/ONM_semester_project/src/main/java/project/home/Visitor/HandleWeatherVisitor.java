package project.home.Visitor;

import project.Reporter.EventReporter;
import project.WeatherEvent.ColdEvent;
import project.WeatherEvent.HotEvent;
import project.WeatherEvent.RainEvent;
import project.WeatherEvent.SnowstormEvent;
import project.WeatherEvent.WindEvent;
import project.home.Device.Device;
import project.home.Window.Window;
import java.util.ArrayList;

/**
 *
 * @author �t�p�n Otta
 */
public class HandleWeatherVisitor implements WeatherEventVisitor {
    private ArrayList<Window> windows;
    private ArrayList<Device> devices;
    private EventReporter er;

    public HandleWeatherVisitor(ArrayList<Window> window, ArrayList<Device> devices, EventReporter er) {
        this.windows = window;
        this.devices = devices;
        this.er = er;
    }

    public void visitColdEvent(ColdEvent coldEvent) {
        
    }

    public void visitHotEvent(HotEvent hotEvent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitRainEvent(RainEvent rainEvent) {
        for(Window w : windows){
            w.close();
        }
        if(rainEvent.isLightning() && rainEvent.getStrength()>80){
            er.fusesReport();
            for (Device d : devices) {
                d.turnOff();
            }
        }
    }

    public void visitsnowStormEvent(SnowstormEvent snowstromEvent) {
        for(Window w : windows){
            w.close();
        }
    }

    public void visitWindEvent(WindEvent windEvent) {
        for(Window w : windows){
            w.close();
        }
    }
    
}

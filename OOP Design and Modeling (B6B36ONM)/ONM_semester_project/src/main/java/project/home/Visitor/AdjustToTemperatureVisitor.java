package project.home.Visitor;

import project.Time.WorldState;
import project.home.Facilities.AirConditioner;
import project.home.Facilities.Fuses;
import project.home.Facilities.Heating;
import project.home.Window.Window;
import java.util.ArrayList;

/**
 *
 * @author �t�p�n Otta
 */
public class AdjustToTemperatureVisitor implements FacilityVisitor {
    private WorldState ws;
    private ArrayList<Window> windows;

    public AdjustToTemperatureVisitor(WorldState ws, ArrayList<Window> windows) {
        this.ws = ws;
        this.windows = windows;
    }

    public void visitHeating(Heating heating) {
        if(ws.getTemperature() < 10){
            heating.turnOn();
            for(Window w : windows){
                w.close();
            }
        } else if (ws.getTemperature() > 10){
            heating.turnOff();
        }
    }

    public void visitAirConditioner(AirConditioner airConditioner) {
        if(ws.getTemperature() > 20){
            airConditioner.turnOn();
            for(Window w : windows){
                w.close();
            }    
        } else if (ws.getTemperature() < 20){
            airConditioner.turnOff();
        }
    }

    public void visitFuses(Fuses fuses) {
        
    }
    
}

package project.home.Visitor;

import project.WeatherEvent.ColdEvent;
import project.WeatherEvent.HotEvent;
import project.WeatherEvent.RainEvent;
import project.WeatherEvent.SnowstormEvent;
import project.WeatherEvent.WindEvent;

/**
 *
 * @author �t�p�n Otta
 */
public interface WeatherEventVisitor {
    
    public void visitColdEvent(ColdEvent coldEvent);
    
    public void visitHotEvent(HotEvent hotEvent);
    
    public void visitRainEvent (RainEvent rainEvent);
    
    public void visitsnowStormEvent (SnowstormEvent snowstromEvent);
    
    public void visitWindEvent(WindEvent windEvent);
}

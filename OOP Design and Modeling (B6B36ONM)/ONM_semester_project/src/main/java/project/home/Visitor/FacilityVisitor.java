package project.home.Visitor;

import project.home.Facilities.AirConditioner;
import project.home.Facilities.Fuses;
import project.home.Facilities.Heating;

/**
 *
 * @author �t�p�n Otta
 */
public interface FacilityVisitor {
    
    public void visitHeating(Heating heating);
    
    public void visitAirConditioner(AirConditioner airConditioner);
    
    public void visitFuses(Fuses fuses);
}

package project.home.Resident;

import project.home.Home;

/**
 *
 * @author �t�p�n Otta
 */
public class Daughter extends Man {
    private final String manType = "Daughter";

    public Daughter(Home home) {
        super(home);
    }
    
    @Override
    public String getManType() {
        return manType;
    }
}

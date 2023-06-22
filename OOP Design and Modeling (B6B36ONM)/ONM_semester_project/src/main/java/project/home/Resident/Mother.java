package project.home.Resident;

import project.home.Home;

/**
 *
 * @author �t�p�n Otta
 */
public class Mother extends Man {
    private final String manType = "Mother";

    public Mother(Home home) {
        super(home);
    }
    
    @Override
    public String getManType() {
        return manType;
    }
}

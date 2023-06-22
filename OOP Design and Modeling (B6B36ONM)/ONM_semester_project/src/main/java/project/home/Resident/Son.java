package project.home.Resident;

import project.home.Home;

/**
 *
 * @author �t�p�n Otta
 */
public class Son extends Man {
    private final String manType = "Son";

    public Son(Home home) {
        super(home);
    }

    @Override
    public String getManType() {
        return manType;
    }

}

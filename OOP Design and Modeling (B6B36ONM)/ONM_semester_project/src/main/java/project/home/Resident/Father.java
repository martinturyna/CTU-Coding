package project.home.Resident;

import project.home.Home;

/**
 *
 * @author �t�p�n Otta
 */
public class Father extends Man {
    private final String manType = "Father";

    public Father(Home home) {
        super(home);
    }

    @Override
    public String getManType() {
        return manType;
    }
}

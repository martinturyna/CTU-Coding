package project;

import project.Simulation.Simulation;

public class Main {
    public static void main(String[] args) {

        int year = 2016;
        int month = 1; // Could be replaced by program input
        int day = 3;
        int hour = 0;
        int duration = 500;

        Simulation simulation = new Simulation(year, month, day, hour, duration);
        simulation.run();

    }
}

package project.Reporter;

import project.home.Device.Device;
import project.home.Facilities.Facility;
import project.home.Floor.Floor;
import project.home.Home;
import project.home.Resident.Animal;
import project.home.Resident.Man;
import project.home.SportResource.SportResource;
import project.home.room.Room;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Creates HouseConfigurationReport.txt file with the configuration of the project.home
 */
public class HouseConfigurationReporter {

    private Home home;
    PrintWriter pw;

    public HouseConfigurationReporter(Home home) {
        this.home = home;
        try {
            pw = new PrintWriter("HouseConfigurationReport.txt", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void createReport(){
        pw.println("The house has " + home.getFloors().size() + " floors");

        for (Floor f : home.getFloors()) {
            pw.println("-- FLOOR " + f.getFloorNumber());
            for (Room r : f.getRooms()) {
                pw.println("-" + r.getRoomType());
                for (Device d: r.getDevices()) {
                    pw.println("    " + d.getDeviceType());
                }
            }
        }
        pw.println();
        pw.println("The facilities in the house are: ");
        for(Facility f : home.getFacilities()){
            pw.println("- " + f.getDeviceType());
        }

        pw.println();
        pw.println("Residents of the house:");
        for (Man m : home.getPeople()) {
            pw.println("-" + m.getManType());
        }
        for (Animal a : home.getAnimals()) {
            pw.println("-" + a.getAnimalType());
        }

        pw.println("Sport equipment in the house: ");
        for (SportResource sr : home.getSportResources()){
            pw.println("-" + sr.getSportResourceType());
        }
        pw.close();
    }

}

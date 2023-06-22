package project.Reporter;

import project.Time.WorldState;
import project.home.Device.Device;
import project.home.Resident.Animal;
import project.home.Resident.Man;
import project.home.SportResource.SportResource;
import project.home.room.Room;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * ActivityAndUsageReporter has all necessary methods for
 * creating logs about any usage of device by a resident
 * or activity such as changing location, being hungry or
 * turning devices on / off.
 */

public class ActivityAndUsageReporter {

    PrintWriter pw;
    WorldState ws;

    public ActivityAndUsageReporter() {
        try {
            this.pw = new PrintWriter("ActivityAndUsageReport.txt", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void printTime(){
        pw.print("[" + ws.getDay() + "/" + ws.getMonth() + "/" + ws.getYear() + "  " + ws.getHour() + ":00] ");
        pw.flush();
    }

    public void changeLocationReport(Man man, Room from, Room to){
        printTime();
        if(from == to)
            pw.println(man.getManType() + " stayed in " + from.getRoomType());
        else
            pw.println(man.getManType() + " moved from " + from.getRoomType() + " to " + to.getRoomType());
        pw.flush();
    }

    public void changeLocationReport(Animal animal, Room from, Room to){
        printTime();
        pw.println(animal.getAnimalType() + " moved from " + from.getRoomType() + " to " + to.getRoomType());
        pw.flush();
    }

    public void openWindowReport(Man man, Room room){
        printTime();
        pw.println(man.getManType() + " opened a window in " + room.getRoomType());
        pw.flush();
    }

    public void deviceUsageReport(Man man, Device device){
        printTime();
        pw.println(man.getManType() + " is using " + device.getDeviceType());
        pw.flush();
    }
    
    public void deviceRepairReport (Man man, Device device){
        printTime();
        pw.println(man.getManType() + " repairs " + device.getDeviceType());
        pw.flush();
    }

    public void sportReport(Man man, SportResource sportResource){
        printTime();
        pw.println(man.getManType() + " is doing sport with " + sportResource.getSportResourceType());
        pw.flush();
    }

    public void sleepReport(Man man){
        printTime();
        pw.println(man.getManType() + " is sleeping");
        pw.flush();
    }

    public void feedReport(Animal animal, Man man){
        printTime();
        pw.println(man.getManType() + " is feeding " + animal.getAnimalType());
        pw.flush();
    }

    public void walkAnimalReport(Man man, Animal  animal){
        printTime();
        pw.println(man.getManType() + " walks " + animal.getAnimalType());
        pw.flush();
    }

    public void hungryAnimalReport(Animal a){
        printTime();
        pw.println(a.getAnimalType() + " is hungry");
        pw.flush();
    }

    public void setWs(WorldState ws) {
        this.ws = ws;
    }
    
    public void fireRunReport(Man man){
        printTime();
        pw.println(man.getManType() + " is running");
        pw.flush();
    }
    
    public void fireCallFireman(Man man){
        printTime();
        pw.println(man.getManType() + " calls Fireman");
        pw.flush();
    }
}

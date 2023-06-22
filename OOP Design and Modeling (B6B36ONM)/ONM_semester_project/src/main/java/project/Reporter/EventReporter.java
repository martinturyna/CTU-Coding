package project.Reporter;

import project.WeatherEvent.WeatherEvent;
import project.Time.WorldState;
import project.home.Device.Device;
import project.home.Device.Sensor.FireSensor;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * EventReporter is class that contains all necessary methods
 * for creating reports about events. Events include
 * weatherEvent, fire, fuses, broken device event.
 * For our reports we have crated .txt files.
 */

public class EventReporter {

    private PrintWriter pw;
    private WorldState ws;

    public EventReporter (WorldState ws) {
        this.ws = ws;

        try {
            this.pw = new PrintWriter("EventReporter.txt", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Standard date format such as [28/5/2017 8:00]
     */
    private void printTime(){
        pw.print("[" + ws.getDay() + "/" + ws.getMonth() + "/" + ws.getYear() + " " + ws.getHour() + ":00] ");
        pw.flush();
    }

    /**
     * weatherReport writes weather data.
     * Standard format: project.WeatherEvent: Wind - Temperature 5, Duration 25hours, Strength<0;100>: 69
     */
    public void weatherReport(WeatherEvent we) {
        printTime();
        pw.println("project.WeatherEvent: " + we.getString() + " - Temperature: " + we.getTemperature() + ", Duration: "
        + we.getDuration() + "hours, Strength<0;100>: " + we.getStrength());
        pw.flush();
    }

    /**
     * brokenDeviceReport writes type of device that has broken and
     * where the device is located.
     * Standard format: BrokenDeviceEvent: AudioPlayer has broken in bathroom
     */
    public void brokenDeviceReport(Device d) {
        printTime();
        pw.println("BrokenDeviceEvent: " + d.getDeviceType() + " has broken in " + d.getLocation().getRoomType());
        pw.flush();
    }

    /**
     * fireReport writes room, where the fire began.
     * Standard format: FireEvent: Fire in bathroom
     */
    public void fireReport(FireSensor fs) {
        printTime();
        pw.println("FireEvent: Fire in " + fs.getRoom());
        pw.flush();
    }

    /**
     * fusesReport writes just simple text and inform about Fuses.
     */
    public void fusesReport() {
        printTime();
        pw.println("FusesEvent: Fuses has turned all devices OFF");
        pw.flush();
    }

}

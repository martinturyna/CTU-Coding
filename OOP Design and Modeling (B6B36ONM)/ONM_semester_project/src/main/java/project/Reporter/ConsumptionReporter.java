package project.Reporter;

import project.Time.WorldState;
import project.home.Device.Device;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ConsumptionReporter {
    private PrintWriter pw;
    private ArrayList<Device> devices;
    private WorldState worldState;
    private int energyConsumption = 0;
    private int energyConsumptionMonth = 0;
    private int energyConsumptionDay = 0;
    private int energyConsumptionHour = 0;

    private int waterConsumption = 0;
    private int waterConsumptionMonth = 0;
    private int waterConsumptionDay = 0;
    private int waterConsumptionHour = 0;

    private int day;
    private int month;

    private static double waterCostPerUnit = 0.005;
    private static double energyCostPerUnit = 0.07;

    public ConsumptionReporter(ArrayList<Device> devices, WorldState worldState) {
        this.devices = devices;
        this.worldState = worldState;
        this.day = this.worldState.getDay();
        this.month = this.worldState.getMonth();
        try {
            this.pw = new PrintWriter("ConsumptionReport.txt", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    
    private void countConsumption(){
        this.energyConsumptionHour = 0;
        waterConsumptionHour = 0;
        if(this.day != this.worldState.getDay()){
            this.waterConsumptionDay = 0;
            this.energyConsumptionDay = 0;
            day = this.worldState.getDay();
        }
        if(this.month != this.worldState.getMonth()){
            this.waterConsumptionMonth = 0;
            this.energyConsumptionMonth = 0;
            this.month = this.worldState.getMonth();
        }
        for(Device d : devices){
            waterConsumptionHour += d.getWaterUse();
            waterConsumptionMonth += d.getWaterUse();
            waterConsumptionDay += d.getWaterUse();
            waterConsumption += d.getWaterUse();

            energyConsumptionHour += d.getEnergyUse();
            energyConsumptionDay += d.getEnergyUse();
            energyConsumptionMonth += d.getEnergyUse();
            energyConsumption += d.getEnergyUse();
        }
    }
    
    public void consumptionReport(){
        countConsumption();
        pw.println("[" + worldState.getDay() + "/" + worldState.getMonth() + "/" + worldState.getYear()
                + "  " + worldState.getHour() + ":00] ");
        pw.println("Energy consumption all time: " + energyConsumption);
        pw.println("Energy consumption this month: " + this.energyConsumptionMonth);
        pw.println("Energy consumption this day: " + this.energyConsumptionDay);
        pw.println("Energy consumption this hour: " + this.energyConsumptionHour);

        pw.println("Water consumption all time: " + waterConsumption);
        pw.println("Water consumption this month: " + waterConsumptionMonth);
        pw.println("Water consumption this day: " + waterConsumptionDay);
        pw.println("Walter consumption this hour: " + waterConsumptionHour);
        pw.flush();
    }

    public void finalReport(){
        pw.println();
        pw.println("Total energy consumption: " + energyConsumption);
        pw.println("Total energy cost: " + energyConsumption*energyCostPerUnit + "$");
        pw.println("Total water consumption was: " + waterConsumption);
        pw.println("Total water cost: " + waterConsumption*waterCostPerUnit + "$");
        pw.flush();

    }
    
}

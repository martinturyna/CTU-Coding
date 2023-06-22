package project.home.Visitor;

import project.home.Device.AudioPlayer;
import project.home.Device.Computer;
import project.home.Device.Cooker;
import project.home.Device.Freezer;
import project.home.Device.Fridge;
import project.home.Device.Microwave;
import project.home.Device.Television;
import project.home.Device.VacuumCleaner;
import project.home.Device.WashingMachine;

/**
 *
 * @author �t�p�n Otta
 */
public class ManTurnOffDeviceVisitor implements DeviceVisitor{

    public void visitFridge(Fridge fridge) {
        fridge.turnIdle();
    }

    public void visitComputer(Computer computer) {
        computer.turnOff();
    }

    public void visitAudioPlayer(AudioPlayer audioPlayer) {
        audioPlayer.turnOff();
    }

    public void visitCooker(Cooker cooker) {
        cooker.turnOff();
    }

    public void visitFreezer(Freezer freezer) {
        freezer.turnIdle();
    }

    public void visitMicrowave(Microwave microwave) {
        microwave.turnOff();
    }

    public void visitTelevision(Television television) {
        television.turnOff();
    }

    public void visitVacuumCleaner(VacuumCleaner vacuumCleaner) {
        vacuumCleaner.turnOff();
    }

    public void visitWashingMachine(WashingMachine washingMachine) {
        washingMachine.turnOff();
    }
    
}

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
public interface DeviceVisitor {
    
    public void visitFridge(Fridge fridge);
    
    public void visitComputer(Computer computer);
    
    public void visitAudioPlayer(AudioPlayer audioPlayer);
    
    public void visitCooker(Cooker cooker);
    
    public void visitFreezer(Freezer freezer);
    
    public void visitMicrowave(Microwave microwace);
    
    public void visitTelevision(Television television);
    
    public void visitVacuumCleaner(VacuumCleaner vacuumCleaner);
    
    public void visitWashingMachine(WashingMachine washingMachine);
}

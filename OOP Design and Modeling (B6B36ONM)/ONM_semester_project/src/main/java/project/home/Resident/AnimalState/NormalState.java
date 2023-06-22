package project.home.Resident.AnimalState;

import project.home.Resident.Animal;
import project.home.room.Room;

import java.util.Random;

/**
 * Created by Csaba on 12/20/2017.
 */
public class NormalState extends AnimalState{
    public NormalState(Animal animal) {
        super(animal);
    }

    public void doSomething() {
        if(animal.isOccupied() == true){
            animal.setOccupied(false);
            return;
        }
        int time = animal.getHome().getWorldState().getHour();
        if(time > 22 || time < 8){
            animal.setOccupied(true);
            return;
        }
        Random rand = new Random();
        Room from = animal.getLocation();
        int tmp = rand.nextInt(animal.getHome().getFloors().size());
        int tmp2 = rand.nextInt(animal.getHome().getFloors().get(tmp).getRooms().size());
        animal.setLocation(animal.getHome().getFloors().get(tmp).getRooms().get(tmp2));
        animal.getHome().getAur().changeLocationReport(animal, from, animal.getLocation());
        if(animal.isHungry())animal.setState(new HungryState(animal));
        else if(animal.isNeedWalk())animal.setState(new NeedWalkState(animal));
    }
}

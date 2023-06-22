package project.home.Resident.AnimalState;

import project.home.Resident.Animal;
import project.home.Resident.Man;


public class HungryState extends AnimalState {

    boolean wasFed = false;

    public HungryState(Animal animal) {
        super(animal);
    }

    public void doSomething() {
        animal.getHome().getAur().hungryAnimalReport(animal);
        for(Man m : animal.getHome().getPeople()){
            if(!m.isOccupied()){
                m.feedAnimal(animal);
                wasFed = true;
                break;
            }
        }
        if(wasFed){
            if(animal.isNeedWalk())animal.setState(new NeedWalkState(animal));
            else animal.setState(new NormalState(animal));
        }
    }
}

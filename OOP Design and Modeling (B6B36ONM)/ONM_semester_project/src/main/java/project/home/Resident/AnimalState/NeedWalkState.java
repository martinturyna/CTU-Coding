package project.home.Resident.AnimalState;

import project.home.Resident.Animal;
import project.home.Resident.Man;

/**
 * Created by Csaba on 12/20/2017.
 */
public class NeedWalkState extends AnimalState{
    boolean wasWalked = false;

    public NeedWalkState(Animal animal) {
        super(animal);
    }

    public void doSomething() {
        for(Man m : animal.getHome().getPeople()){
            if(!m.isOccupied()){
                m.walkAnimal(animal);
                wasWalked = true;
                break;
            }
        }

        if(wasWalked){
            if(animal.isHungry())animal.setState(new HungryState(animal));
            else animal.setState(new NormalState(animal));
        }
    }
}

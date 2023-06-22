package project.home.Resident.AnimalState;

import project.home.Resident.Animal;

/**
 * There are 3 states for animals: Hungry, NeedWalk, Satisfied
 */
public abstract class AnimalState {

    protected Animal animal;

    public AnimalState(Animal animal) {
        this.animal = animal;
    }

    public abstract void doSomething();
}

package project.home.Resident;

import project.home.Home;
import project.home.Resident.AnimalState.AnimalState;
import project.home.Resident.AnimalState.NormalState;
import project.home.room.Room;

/**
 * Abstract class for animal. Every animal have to extend this class.
 * Carry information in common for all animals.
 * Their location, hunger, walkNeed.
 * @author Otta
 */
public abstract class Animal {

    /**
     *  Represents the level of animal hunger, 
     *  when it hits zero Animal becomes hungry.
     */
    protected int hunger = 100;

    /**
     * Represents how much the animal needs to
     * go for a walk, when it hits zero Man have
     * to walk animal.
     */
    protected int walkNeed = 100;

    /**
     *  Represents actual location of animal in house.
     */
    protected Room location;

    /**
     * Represents project.home animal belongs to.
     */
    protected Home home;

    /**
     *  Show if animal is hungry. When it becomes 
     *  true Man have to feed the animal.
     */
    protected boolean hungry = false;

    /**
     *  Show if animal needs to go for a walk. When
     *  it becomes true man have to walk animal.
     */
    protected boolean needWalk = false;

    /**
     *  If this is true, it means that animal
     *  already made some action this hour.
     */
    protected boolean occupied = false;

    /**
     *  This represents state of the animal.
     *  It can be either Hungry, NeedWalk or Normal.
     */
    protected AnimalState state;

    /**
     * Animal constructor. When new animal is made,
     * you have to give it project.home.
     * The state is is set to normal, when animal is
     * made.
     * @param home
     */
    public Animal(Home home) {
        this.home = home;
        state = new NormalState(this);
    }
    
    /**
     * Returns the type of concrete animal.
     * @return
     */
    public abstract String getAnimalType();

    /**
     * Returns animal current location.
     * @return
     */
    public Room getLocation() {
        return location;
    }

    /**
     * Returns true if animal is hungry, false othrewise.
     * @return
     */
    public boolean isHungry() {
        return hungry;
    }

    /**
     * If sets to true it makes animal Hungry.
     * @param hungry
     */
    public void setHungry(boolean hungry) {
        this.hungry = hungry;
    }

    /**
     * If animal needs walk return true.
     * @return
     */
    public boolean isNeedWalk() {
        return needWalk;
    }

    /**
     * If sets to true, animal needs to go for a walk then.
     * @param needWalk
     */
    public void setNeedWalk(boolean needWalk) {
        this.needWalk = needWalk;
    }

    /**
     *  Random generating animal activity.
     */
    public void doSomething() {
        updateNeeds();
        state.doSomething();
    }

    /**
     * Feeds the animal.
     */
    public void Feed() {
        occupied = true;
        this.hungry = false;
        this.hunger = 100;
    }

    /**
     *  Takes animal for a walk.
     */
    public void goForWalk() {
        occupied = true;
        this.needWalk = false;
        this.walkNeed = 100;
    }

    /**
     * Sets current location of animal.
     * @param location
     */
    public void setLocation(Room location) {
        this.location = location;
    }
    
    /**
     *  Deacrese animal hunger and walkNeed.
     */
    public void updateNeeds(){
        hunger = hunger -5;
        if(hunger <=0){
            this.hungry = true;
        }
        walkNeed = walkNeed - 8;
        if(walkNeed <= 0){
            this.needWalk = true;
        }
    }

    /**
     * Set the state of animal.
     * Hungry/needWalk/Normal.
     * @param state
     */
    public void setState(AnimalState state) {
        this.state = state;
    }

    /**
     * Returns project.home of the animal.
     * @return
     */
    public Home getHome() {
        return home;
    }

    /**
     * Returns true if animal already performed any action this hour.
     * @return
     */
    public boolean isOccupied() {
        return occupied;
    }

    /**
     * If sets to true, animal will not be able to make
     * any actions this hour.
     * @param occupied
     */
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}

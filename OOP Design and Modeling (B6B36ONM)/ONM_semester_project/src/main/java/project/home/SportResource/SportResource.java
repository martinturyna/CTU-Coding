package project.home.SportResource;

/**
 *  Abstract class for sport resource. Every sport resource have to
 *  extends this class.
 *  Carry information in common for all sport resources,
 *  their state and sportResouceType.
 * @author Otta
 */
public abstract class SportResource {

    /**
     *  Represents state of the sport resource.
     *  Can be inUse, ready.
     */
    protected SportResourceState state;

    /**
     *  Represents the type of sport resource.
     */
    protected String sportResourceType;

    /**
     *  
     */
    public SportResource() {
        state = new SportResourceReady(this);
    }
    
    /**
     * Make the sport resource being used for sport.
     * @return
     */
    public boolean goSport(){
        if(state.use()){
            return true;
        }
        return false;
    }
    
    /**
     *  Make the sport resource being returned to project.home.
     */
    public void goBackHome(){
        state.makeReady();
    }

    /**
     * Returns current stat of the sport resource.
     * @return
     */
    public SportResourceState getState() {
        return state;
    }

    /**
     * Sets the current state of sport resource.
     * @param state
     */
    public void setState(SportResourceState state) {
        this.state = state;
    }

    /**
     *  Returns the sport resource type.
     * @return
     */
    public String getSportResourceType() {
        return sportResourceType;
    }
}

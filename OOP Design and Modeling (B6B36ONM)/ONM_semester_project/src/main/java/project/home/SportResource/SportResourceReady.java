package project.home.SportResource;

/**
 *
 * @author �t�p�n Otta
 */
public class SportResourceReady implements SportResourceState {
    private final SportResource sportResource;

    public SportResourceReady(SportResource sportResource) {
        this.sportResource = sportResource;
    }

    public boolean use() {
        sportResource.setState(new SportResourceInUse(sportResource));
        return true;
    }

    public boolean makeReady() {
        return false;
    }
    
}

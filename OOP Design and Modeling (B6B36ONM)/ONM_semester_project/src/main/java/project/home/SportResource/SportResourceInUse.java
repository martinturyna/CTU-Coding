package project.home.SportResource;

/**
 *
 * @author �t�p�n Otta
 */
public class SportResourceInUse implements SportResourceState {
    private final SportResource sportResource;

    public SportResourceInUse(SportResource sportResource) {
        this.sportResource = sportResource;
    }

    public boolean use() {
        return false;
    }

    public boolean makeReady() {
        sportResource.setState(new SportResourceReady(sportResource));
        return true;
    }
    
}

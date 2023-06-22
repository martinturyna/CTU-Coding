package project.home;

/**
 *
 * @author �t�p�n Otta
 */
public interface Observable {
    
    public void attach(Observer observer);
    
    public void detach(Observer observer);
    
    public void notifyAllObservers();
}

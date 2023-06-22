package project.home.Resident;

import project.home.Home;

/**
 *
 * @author �t�p�n Otta
 */
public class Dog extends Animal {
    private final String animalType = "Dog";
    
    public Dog(Home home) {
        super(home);
    }

    @Override
    public String getAnimalType() {
        return animalType;
    }
    
}

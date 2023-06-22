package project.home.Resident;

import project.home.Home;

/**
 *
 * @author �t�p�n Otta
 */
public class Cat extends Animal {
    private final String animalType = "Cat";
    
    public Cat(Home home) {
        super(home);
    }

    @Override
    public String getAnimalType() {
        return animalType;
    }
    
}

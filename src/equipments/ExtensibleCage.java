package equipments;

import animals.Animal;
import animals.Herbivore;
import animals.Predator;

import java.util.ArrayList;
import java.util.List;

public class ExtensibleCage<T extends Animal> {
    private List<T> listCage = new ArrayList<>();

    public int addAnimal(T animal) {
        listCage.add(animal);
        animal.setCage(this);
        return listCage.size();
    }

    public boolean removeAnimal(int number) {
        if (number < 0 || number > listCage.size() - 1)
            return false;
        else return listCage.remove(number) != null;
    }

    public List<T> getAnimals() {
        return listCage;
    }

    public int getAnimalsCounter() {
        return listCage.size();
    }

    public void checkHuntCondition(Animal animal) {
        if (animal instanceof Herbivore) {
            Animal other;
            Predator hunter = null;
            double minFill = Double.MAX_VALUE;
            for (int i = 0; i < animal.getCage().getAnimalsCounter(); i++) {
                other = animal.getCage().getAnimals().get(i);
                double otherFill = other.getFill();
                if (otherFill > 0 && other instanceof Predator && otherFill < minFill) {
                    hunter = (Predator) other;
                    minFill = otherFill;
                } else if (otherFill <= 0) i--;
            }
            if(hunter != null) hunter.consume((Herbivore) animal);
        }
    }
}

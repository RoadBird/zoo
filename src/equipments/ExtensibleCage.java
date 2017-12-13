package equipments;

import animals.Animal;
import java.util.ArrayList;
import java.util.List;

public class ExtensibleCage<T extends Animal> {
    private List<T> listCage = new ArrayList<>();

    public int addAnimal(T animal) {
        listCage.add(animal);
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
}

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
            return listCage.remove(number) != null;
        else return false;
    }

    public Animal[] getAnimals() {
        //return listCage.toArray(new Animal[listCage.size()]);

        Animal[] animals = new Animal[listCage.size()];
        for (int i = 0; i < animals.length; i++) {
            animals[i] = listCage.get(i);
        }
        return animals;
    }

    public int getAnimalsCounter() {
        return listCage.size();
    }
}

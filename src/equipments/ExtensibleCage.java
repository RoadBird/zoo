package equipments;

import animals.Animal;

import java.util.ArrayList;
import java.util.List;

public class ExtensibleCage<T extends Animal> {
    private List<Animal> listCage = new ArrayList<>();

    public int addAnimal(Animal animal) {
        listCage.add(animal);
        return listCage.size();
    }

    public boolean removeAnimal(int number) {
        return listCage.remove(number) != null;
    }

    public Animal[] getAnimals() {
        Animal[] animals = new Animal[listCage.size()];
        for(int i = 0; i < animals.length; i++){
            animals[i] = listCage.get(i);
        }
        return animals;
    }
}

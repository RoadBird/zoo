package equipments;

import animals.Animal;

public class ExtensibleCage {
    private Animal[] cage = new Animal[0];
    private int animalsCounter = 0;

    public int addAnimal(Animal animal) {
        if (cage.length == 0) {
            cage = new Animal[]{animal};
            return ++animalsCounter;
        }
        if (animalsCounter == cage.length) {
            Animal[] temp = new Animal[cage.length * 2];
            for (int i = 0; i < cage.length; i++)
                temp[i] = cage[i];
            cage = temp;
        }
        cage[animalsCounter] = animal;
        return ++animalsCounter;
    }

    public Animal[] getAnimals() {
        return cage;
    }
}

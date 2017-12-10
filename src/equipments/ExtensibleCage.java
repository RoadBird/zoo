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

    public boolean removeAnimal(int number) {
        if (number > 0 && number <= animalsCounter) {
            cage[number - 1] = null;
            for (int i = number - 1; i < animalsCounter - 1; i++) {
                cage[i] = cage[i + 1];
                cage[i + 1] = null;
            }
            animalsCounter--;
            return true;
        } else return false;
    }

    public Animal[] getAnimals() {
        return cage;
    }

    public int getAnimalsCounter() {
        return animalsCounter;
    }
}

package equipments;

import animals.Animal;
import animals.Herbivore;
import animals.Predator;

import java.util.*;

public class ExtensibleCage<T extends Animal> implements Animal.IAnimalDeadListener {
    private List<T> listCage = new ArrayList<>();
    private String type;

    public ExtensibleCage(String type) {
        this.type = type;
    }

    public int addAnimal(Animal animal) {
        listCage.add((T) animal);
        animal.setAnimalDeadListener(this);
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
            Predator hunter = null;
            double minFill = Double.MAX_VALUE;
            Iterator iterator = getAnimals().iterator();
            while (iterator.hasNext()) {
                Animal t = (Animal) iterator.next();
                double otherFill = t.getFill();
                if (otherFill > 0 && t instanceof Predator && otherFill < minFill) {
                    hunter = (Predator) t;
                    minFill = otherFill;
                } else if (otherFill <= 0) iterator.remove();
            }
            if (hunter != null) hunter.consume((Herbivore) animal);
        } else if (animal instanceof Predator) {

            Iterator iterator = getAnimals().iterator();
            while (iterator.hasNext()) {
                Animal secont = (Animal) iterator.next();
                if (secont instanceof Herbivore) {
                    iterator.remove();
                    ((Predator) animal).consume((Herbivore) secont);
                }
            }
        }
    }

    public String getType() {
        return type;
    }

    @Override
    //Нехило так должно жрать память,
    // если удаление произошло через итератор перед вызовом метода
    public void onAnimalDead(Animal animal) {
        for (int i = 0; i < getAnimalsCounter(); i++) {
            if (getAnimals().get(i).equals(animal)) {
                System.out.println(getAnimals().get(i).toString() + " is dead");
                removeAnimal(i);
                return;
            }
        }
    }

    public String getCageInfo() {
        sortAnimals();
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<? extends Animal> it = listCage.iterator();
        while (it.hasNext()) {
            Animal animal = it.next();
            if (animal.getNickName() == null || animal.getNickName().length() == 0)
                it.remove();
            else stringBuilder.append(animal.toString());
        }
        return stringBuilder.toString();
    }

    public void sortAnimals(){
        Collections.sort(listCage);
    }

    private static class SizeComparator implements Comparator<Animal> {

        @Override
        public int compare(Animal o1, Animal o2) {
            return (int) Math.ceil(o1.getSize() - o2.getSize());
        }
    }
}

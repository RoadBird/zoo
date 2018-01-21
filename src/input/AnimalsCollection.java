package input;

import animals.Animal;
import error.AnimalCreationException;
import error.AnimalInvalidTypeException;

import java.util.Random;
import java.util.UUID;

public class AnimalsCollection {
    public static Animal createAnimal(String type) throws AnimalCreationException {
        Class<? extends Animal> clazz;
        try {
            clazz = (Class<? extends Animal>) Class.forName("animals." + type);
        } catch (ClassNotFoundException e) {
            throw new AnimalInvalidTypeException();
        }
        if (clazz == null)
            throw new AnimalInvalidTypeException();
        Animal t = null;
        try {
            t = clazz.newInstance();
            t.setNickName("Animal " + UUID.randomUUID().toString());
            Random r = new Random();
            t.setSize(20 + r.nextInt(10) - r.nextInt(10));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (t == null)
            throw new AnimalCreationException("Generic animal creation failed call support");
        return t;
    }
}

package input;

import animals.*;
import error.AnimalCreationException;
import error.AnimalInvalidTypeException;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class AnimalsCollection {
    static Map<String, Class<? extends Animal>> map = new HashMap<>();

    static {
        map.put("Cat", HouseCat.class);
        map.put("Wolf", ForestWolf.class);
        map.put("Rabbit", Rabbit.class);
        map.put("Raven", Raven.class);
    }

    private static Class<? extends Animal> getAnimalClass(String type) throws AnimalCreationException{
        Class<? extends Animal> animalClass = map.get(type);
        if(animalClass == null)
            throw new AnimalInvalidTypeException();
        else return animalClass;
    }

    public static Animal createAnimal(String type) throws AnimalCreationException {
        Class <? extends Animal> clazz= getAnimalClass(type);
        if(clazz == null)
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
        if(t == null)
            throw new AnimalCreationException("Generic animal creation failed call support");
        return t;
    }
}

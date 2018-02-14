package animals;

import error.AnimalCreationException;
import error.AnimalInvalidTypeException;
import food.Food;
import interfases.Jumpable;
import interfases.Soundable;
import meta.FillRetention;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.UUID;

public abstract class Animal implements Soundable, Jumpable, Comparable<Animal> {
    public static final int INITIAL_FILL = 120;
    private IAnimalDeadListener animalDeadListener;
    private double size;
    private String nickName;
    protected String type;
    private double fill;
    protected final long createdAt;
    private boolean isAlive;
    private boolean isStarving;

    protected Animal() {
        setFill(125);
        createdAt = System.currentTimeMillis();
        isAlive = true;
    }

    public Animal(double size, String nickName) {
        setSize(size);
        setNickName(nickName);
        setFill(125);
        createdAt = System.currentTimeMillis();
        isAlive = true;
    }

    @Override
    public String toString() {
        return "Animal " + this.getType() + ": " + this.getNickName() + ", fill = " + this.getFill();
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public final String getType() {
        return type;
    }

    public double getFill() {
        return fill;
    }

    public void setFill(double fill) {
        this.fill = fill;
        if(getFill() > 20){
            setStarving(false);
        }
    }

    public boolean isStarving() {
        return isStarving;
    }

    public void setStarving(boolean starving) {
        isStarving = starving;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAnimalDeadListener(IAnimalDeadListener animalDeadListener) {
        this.animalDeadListener = animalDeadListener;
    }

    public double feed(Food food) {
        double temp = getFill();
        switch (food) {
            case MEAT:
                System.out.println("Mmmmmmm, meat");
                break;
            case CARROT:
                System.out.println("Om, carrot");
                break;
            default:
                return getFill();
        }
        if (temp > 0)
            setFill(temp + food.getEnergyValue());
        return getFill();
    }

    public void die() {
        isAlive = false;
        if (animalDeadListener != null) animalDeadListener.onAnimalDead(this);
    }

    public static <ANIMAL_TYPE extends Animal> ANIMAL_TYPE createAnimal(String type, double size, String name) throws AnimalCreationException {
        try {
            Class<? extends Animal> clazz = (Class<? extends Animal>) Class.forName("animals." + type);
            Constructor constructor = clazz.getConstructor(double.class, String.class);
            ANIMAL_TYPE animal = (ANIMAL_TYPE) constructor.newInstance(size, name);
            return animal;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new AnimalInvalidTypeException();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        throw new AnimalCreationException("Generic animal creation failed call support");
    }

    public interface IAnimalDeadListener {
        void onAnimalDead(Animal animal);
    }

    public int compareTo(Animal other) {
        return (int) Math.ceil(this.getSize() - other.getSize());
    }
}

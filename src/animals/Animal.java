package animals;

import error.AnimalCreationException;
import error.AnimalInvalidTypeException;
import food.Food;
import interfases.Jumpable;
import interfases.Soundable;

import java.util.Random;
import java.util.UUID;

public abstract class Animal implements Soundable, Jumpable, Comparable<Animal> {
    private IAnimalDeadListener animalDeadListener;
    private double size;
    private String nickName;
    protected String type;
    private double fill;
    protected final long createdAt;
    private long lastFeedTime;
    private boolean isAlive;

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
        return "Animal " + this.getType() + ": " + this.getNickName();
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
        double timeSec = (System.currentTimeMillis() - lastFeedTime) / 1000;
        if (fill - timeSec <= 0) {
            die();
        }
        return fill - timeSec;
    }

    public void setFill(double fill) {
        this.fill = fill;
        lastFeedTime = System.currentTimeMillis();
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

    protected void die() {
        isAlive = false;
        if (animalDeadListener != null) animalDeadListener.onAnimalDead(this);
    }

    public static Animal createAnimal(String type) throws AnimalCreationException {
        try {
            Class<? extends Animal> clazz = (Class<? extends Animal>) Class.forName("animals." + type);
            Animal t = clazz.newInstance();
            t.setNickName("Animal " + UUID.randomUUID().toString());
            Random r = new Random();
            t.setSize(20 + r.nextInt(10) - r.nextInt(10));
            return t;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new AnimalInvalidTypeException();
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

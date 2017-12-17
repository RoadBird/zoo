package animals;

import equipments.ExtensibleCage;
import interfases.Jumpable;
import interfases.Soundable;

import javax.naming.ldap.ExtendedRequest;


public abstract class Animal implements Soundable, Jumpable {
    private IAnimalDeadListener animalDeadListener;
    private double size;
    private String nickName;
    protected String type;
    private double fill;
    protected final long createdAt;
    private long lastFeedTime;
    private boolean isAlive;
    private ExtensibleCage<?> cage;

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
        setFill(fill - timeSec);
        if (fill <= 0) {
            isAlive = false;
            if(animalDeadListener != null) animalDeadListener.onAnimalDead(this);
        }
        return fill;
    }

    private void setFill(double fill) {
        this.fill = fill;
        lastFeedTime = System.currentTimeMillis();
    }

    public boolean isAlive() {
        return isAlive;
    }

    public ExtensibleCage<?> getCage() {
        return cage;
    }

    public void setCage(ExtensibleCage<?> cage) {
        this.cage = cage;
    }

    public void setAnimalDeadListener(IAnimalDeadListener animalDeadListener) {
        this.animalDeadListener = animalDeadListener;
    }

    public double feed(double val) {
        double temp = getFill();
        if (temp > 0)
            setFill(temp + val);
        return getFill();
    }

    public interface IAnimalDeadListener {
        void onAnimalDead(Animal animal);
    }

}

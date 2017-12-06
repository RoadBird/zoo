package animals;

import interfases.Jumpable;
import interfases.Soundable;

public abstract class Animal implements Soundable, Jumpable {
    private double size;
    private String nickName;
    protected String type;
    private double fill;
    protected final long createdAt;
    private long lastFeedTime;

    public Animal(double size, String nickName) {
        setSize(size);
        setNickName(nickName);
        setFill(120);
        createdAt = System.currentTimeMillis();
    }

    @Override
    public String toString(){
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
        return fill;
    }

    public void setFill(double fill) {
        this.fill = fill;
        lastFeedTime = System.currentTimeMillis();
    }

    public double feed(double val){
        setFill(getFill() + val);
        return getFill();
    }

}

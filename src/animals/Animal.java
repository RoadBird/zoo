package animals;

import interfases.Jumpable;
import interfases.Soundable;

public abstract class Animal implements Soundable, Jumpable {
    private double size;
    private String nickName;
    protected String type;
    protected double fill;

    public Animal(double size, String nickName) {
        setSize(size);
        setNickName(nickName);
        setFill(120.5);
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
    }

    public void feed(double val){
        setFill(getFill() + val);
    }
}

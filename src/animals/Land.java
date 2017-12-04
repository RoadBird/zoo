package animals;

public abstract class Land extends Mammals {
    private int limbs;

    public Land(double size, String nickNames) {
        super(size, nickNames);
    }

    public int getLimbs() {
        return limbs;
    }

    public void setLimbs(int limbs) {
        this.limbs = limbs;
    }
}

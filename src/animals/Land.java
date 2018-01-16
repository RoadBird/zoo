package animals;

public abstract class Land extends Mammals {
    private int limbs;

    protected Land() {
        super();
    }

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

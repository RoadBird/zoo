package animals;

public abstract class Predator extends Land {
    private boolean isScavenger;

    public Predator(double size, String nickNames) {
        super(size, nickNames);
    }

    public boolean isScavenger() {
        return isScavenger;
    }

    public void setScavenger(boolean scavenger) {
        isScavenger = scavenger;
    }
}

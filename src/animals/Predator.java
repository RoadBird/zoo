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

    public void consume(Herbivore prey){
        this.feed(prey.getSize());
        System.out.println(this.toString() + " ate " + prey.toString());
        prey.onConsumed();
    }
}

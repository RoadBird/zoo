package animals;

import food.Food;

public abstract class Predator extends Land {
    private boolean isScavenger;

    protected Predator() {
        super();
    }

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
        this.feed(Food.MEAT);
        System.out.println(this.toString() + " ate " + prey.toString());
        prey.onConsumed();
    }
}

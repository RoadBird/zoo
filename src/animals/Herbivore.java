package animals;

public abstract class Herbivore extends Land {

    protected Herbivore() {
        super();
    }

    public Herbivore(double size, String nickNames) {
        super(size, nickNames);
    }

    public void onConsumed(){
        die();
    }
}

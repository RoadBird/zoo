package animals;

public abstract class Herbivore extends Land {
    public Herbivore(double size, String nickNames) {
        super(size, nickNames);
    }

    public void onConsumed(){
        for (int i = 0; i < this.getCage().getAnimalsCounter(); i++) {
            if (this.getCage().getAnimals().get(i).equals(this)) {
                this.getCage().removeAnimal(i);
                return;
            }
        }
        System.out.println("There is no the animal in his cage");
    }
}

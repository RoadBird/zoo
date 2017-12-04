package animals;


public final class HouseCat extends Feline{
    private String breed;
    private final String SOUND = "Meow!";

    public HouseCat(double size, String nickNames) {
        super(size, nickNames);
        type = "Cat";
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    @Override
    public void sound() {
        System.out.println(SOUND);
    }

    @Override
    public double jump() {
        return getSize() * 2.14;
    }
}

package animals;


import error.AnimalCreationException;
import main.Input;
import main.Main;

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

    public static HouseCat createCat() {
        double size = Input.megaInputNumber("Enter size of the new Cat");
        if(size < 0) size = 42;
        System.out.println("And his Name, please");
        String name = Main.scan.nextLine();
        if(name.length() == 0)
            throw new AnimalCreationException();
        return new HouseCat(size, name);
    }
}

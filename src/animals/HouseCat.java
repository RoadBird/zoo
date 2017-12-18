package animals;


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
        System.out.println("Enter size of the new Cat");
        double size = Main.scan.nextDouble();
        Main.scan.nextLine();
        if(size < 0) size = 42;
        System.out.println("And his Name, please");
        return new HouseCat(size, Main.scan.nextLine());
    }
}

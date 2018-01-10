package animals;

import main.Input;
import main.Main;

public class Rabbit extends Herbivore{

    public Rabbit(double size, String nickNames) {
        super(size, nickNames);
        type = "Rabbit";
    }

    @Override
    public double jump() {
        return getSize() * 2.5;
    }

    @Override
    public void sound() {
        System.out.println("Snuff");
    }

    public static Rabbit createRabbit() {
        double size = Input.megaInputNumber("Enter size of the new Rabbit");
        if(size < 0) size = 42;
        System.out.println("And his Name, please");
        return new Rabbit(size, Main.scan.nextLine());
    }
}

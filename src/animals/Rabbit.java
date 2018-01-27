package animals;

import input.Input;
import main.Main;

public class Rabbit extends Herbivore{

    public Rabbit(){
        super();
        type = this.getClass().getSimpleName();
    }

    public Rabbit(double size, String nickNames) {
        super(size, nickNames);
        type = this.getClass().getSimpleName();
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

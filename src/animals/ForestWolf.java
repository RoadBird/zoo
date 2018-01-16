package animals;

import input.Input;
import main.Main;


public class ForestWolf extends Canine {
    public ForestWolf(){
        super();
        type = "Wolf";
    }
    public ForestWolf(double size, String nickNames) {
        super(size, nickNames);
        type = "Wolf";
    }

    @Override
    public void sound() {
        System.out.println("Woof");
    }

    @Override
    public double jump() {
        return getSize() * 3.67;
    }

    public static ForestWolf createWolf() {
        double size = Input.megaInputNumber("Enter size of the new Wolf");
        if(size < 0) size = 42;
        System.out.println("And his Name, please");
        return new ForestWolf(size, Main.scan.nextLine());
    }
}

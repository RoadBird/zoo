package animals;

import input.Input;
import main.Main;

public class Raven extends Bird {
    public Raven(){
        super();
        type = this.getClass().getSimpleName();
    }

    public Raven(double size, String nickName) {
        super(size, nickName);
        type = this.getClass().getSimpleName();
    }

    @Override
    public double jump() {
        return 6;
    }

    @Override
    public void sound() {
        System.out.println("ARR!");
    }

    public static Raven createRaven() {
        double size = Input.megaInputNumber("Enter size of the new Raven");
        if(size < 0) size = 42;
        System.out.println("And his Name, please");
        return new Raven(size, Main.scan.nextLine());
    }
}

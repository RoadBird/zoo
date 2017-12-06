package animals;

import main.Main;


public class ForestWolf extends Canine {
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
        System.out.println("Enter size of the new Wolf");
        double size = Main.scan.nextDouble();
        Main.scan.nextLine();
        System.out.println("And his Name, please");
        return new ForestWolf(size, Main.scan.nextLine());
    }
}

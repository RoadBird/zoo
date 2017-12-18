package animals;

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
        System.out.println("Enter size of the new Rabbit");
        double size = Main.scan.nextDouble();
        Main.scan.nextLine();
        if(size < 0) size = 42;
        System.out.println("And his Name, please");
        return new Rabbit(size, Main.scan.nextLine());
    }
}

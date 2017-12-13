package animals;

import main.Main;

public class Raven extends Bird {
    public Raven(double size, String nickName) {
        super(size, nickName);
        type = "Raven";
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
        System.out.println("Enter size of the new Raven");
        double size = Main.scan.nextDouble();
        Main.scan.nextLine();
        System.out.println("And his Name, please");
        return new Raven(size, Main.scan.nextLine());
    }
}

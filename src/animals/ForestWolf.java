package animals;

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
}

package animals;

public abstract class Mammals extends Animal {
    private int pregnantPeriodMonthes;

    public Mammals(double size, String nickName) {
        super(size, nickName);
    }

    public int getPregnantPeriodMonthes() {
        return pregnantPeriodMonthes;
    }

    public void setPregnantPeriodMonthes(int pregnantPeriodMonthes) {
        this.pregnantPeriodMonthes = pregnantPeriodMonthes;
    }


}

package food;

public enum Food {
    CARROT(30), MEAT(65);
    double energyValue = 10;

    Food(double energyValue){
        this.energyValue = energyValue;
    }

    public double getEnergyValue() {
        return energyValue;
    }
}

package life;

import animals.Animal;
import equipments.ExtensibleCage;

import java.util.Iterator;
import java.util.Map;

public class LifeCheck extends Thread {
    private volatile Boolean isRun = true;
    private static LifeCheck LIFE_CHECK;
    Map<String, ExtensibleCage<? extends Animal>> cages;

    private LifeCheck() {
        start();
    }

    public void setMapOfCages(Map<String, ExtensibleCage<? extends Animal>> cages) {
        this.cages = cages;
    }

    public void stopWork() {
        isRun = false;
    }

    @Override
    public void run() {
        long timeStart = System.currentTimeMillis();
        long timeCheck;
        while (isRun) {
            if (cages != null) {
                Animal animal;
                Iterator<Animal> iter;
                ExtensibleCage<? extends Animal> cage;
                for (String c : cages.keySet()) {
                    cage = cages.get(c);
                    iter = (Iterator<Animal>) cage.getAnimals().iterator();
                    while (iter.hasNext()) {
                        animal = iter.next();
                        timeCheck = System.currentTimeMillis();
                        if(timeCheck - timeStart >= 1000){
                            animal.setFill(animal.getFill() - 1);
                            timeStart = timeCheck;
                        }
                        if (animal.getFill() <= 20 && !animal.isStarving()) {
                            System.out.println(animal + " can die soon. Feed it");
                            animal.setStarving(true);
                        }
                        if(animal.getFill() <= 0){
                            animal.die();
                        }
                    }
                }
            }
        }
    }

    public static LifeCheck newInstace() {
        Class<LifeCheck> cl = LifeCheck.class;
        synchronized (LifeCheck.class) {
            if (LIFE_CHECK == null) {
                LIFE_CHECK = new LifeCheck();
            }
            return LIFE_CHECK;
        }
    }
}

package main;

import animals.*;
import equipments.ExtensibleCage;
import equipments.SingleCage;
import interfases.Soundable;
import javafx.beans.binding.StringBinding;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main implements Animal.IAnimalDeadListener {
    public static final Scanner scan = new Scanner(System.in);
    private final ExtensibleCage<Predator> predatorCage = new ExtensibleCage<>();
    private final ExtensibleCage<Bird> birdCage = new ExtensibleCage<>();

    public Main() {
        System.out.println("Yep, Hello!");
        boolean exit = false;
        do {
            System.out.println("What do you want??\n" +
                    "1 - Look at all in the Cage\n" +
                    "2 - Feed everyone\n" +
                    "3 - Add new Animal\n" +
                    "4 - Let them jump\n" +
                    "5 - Remove Animal\n" +
                    "6 - Touch the cage\n" +
                    "0 - Exit");
            String answer = scan.nextLine();
            switch (answer) {
                case "1":
                    showAnimalInfo();
                    break;
                case "2":
                    feedAnimal();
                    break;
                case "3":
                    Animal animal = createAnimal();
                    if (animal != null) {
                        if (animal instanceof Predator)
                            predatorCage.addAnimal((Predator) animal);
                        else if (animal instanceof Bird)
                            birdCage.addAnimal((Bird) animal);
                    }
                    break;
                case "4":
                    jumpAll();
                    break;
                case "5":
                    removeAnimal();
                    break;
                case "6":
                    makeSound(predatorCage.getAnimals());
                    break;
                case "0":
                    System.out.println("Thanks!");
                    exit = true;
                    break;
                default:
                    System.out.println("What? I don't understand you. Try again");
                    break;
            }
        } while (!exit);
        System.out.println("Exit!");
    }

    public static void main(String[] args) {
        //ExtensibleCage<Animal> cage2[] = new ExtensibleCage<Animal>[10];
        //ExtensibleCage<?> cage2[] = new ExtensibleCage<?>[10];
        new Main();
        scan.close();
    }

    public void showAnimalInfo() {
        StringBuilder stringBuilder = new StringBuilder("\nWho do we have:\n");
        stringBuilder.append("Cage of Predator:\n");
        if(predatorCage.getAnimalsCounter() == 0){
            stringBuilder.append("\tCage is empty\n");
        } else {
            for (Predator predator : predatorCage.getAnimals()) {
                stringBuilder.append("Animal ").append(predator.getType()).append(": ");
                stringBuilder.append(" name - ").append(predator.getNickName()).append(", ");
                stringBuilder.append("siae - ").append(predator.getSize()).append(", ");
                stringBuilder.append("fill - ").append(predator.getFill()).append(".\n");
            }
        }
        stringBuilder.append("Cage of Bird:\n");
        if(birdCage.getAnimalsCounter() == 0){
            stringBuilder.append("\tCage is empty\n");
        } else {
            for (Bird bird : birdCage.getAnimals()) {
                stringBuilder.append("Animal ").append(bird.getType()).append(": ");
                stringBuilder.append(" name - ").append(bird.getNickName()).append(", ");
                stringBuilder.append("siae - ").append(bird.getSize()).append(", ");
                stringBuilder.append("fill - ").append(bird.getFill()).append(".\n");
            }
        }
        System.out.println(stringBuilder.toString());

    }

    public void makeSound(List<? extends Soundable> sounds) {
        if (predatorCage.getAnimalsCounter() < 1) {
            System.out.println("Cage is empty");
            return;
        } else for (Soundable sound : sounds) {
            if (sound != null) sound.sound();
        }
    }

    public void jumpAll() {
        if (predatorCage.getAnimalsCounter() > 0) {
            double tmp = 0;
            int t = 0;
            for (int i = 0; i < predatorCage.getAnimalsCounter(); i++) {
                if (predatorCage.getAnimals().get(i).jump() > tmp) {
                    tmp = predatorCage.getAnimals().get(i).jump();
                    t = i;
                }
            }
            System.out.println(predatorCage.getAnimals().get(t).toString() + " has best jump");
        } else
            System.out.println("None in cage");
    }

    public void feedAnimal() {
        while (true) {
            if (predatorCage.getAnimalsCounter() < 1) {
                System.out.println("Cage is empty");
                return;
            } else {
                System.out.println("Enter the animal number from 1 to " + predatorCage.getAnimalsCounter() + " or 0 to return");
                int num = scan.nextInt();
                scan.nextLine();
                if (num == 0)
                    return;
                else if (num > 0 && num <= predatorCage.getAnimalsCounter()) {
                    System.out.println(predatorCage.getAnimals().get(num - 1).toString() +
                            " have new fill is " + predatorCage.getAnimals().get(num - 1).feed(50));
                    return;
                } else
                    System.out.println("Wrong number cage");
            }
        }
    }

    public Animal createAnimal() {
        Animal animal;
        while (true) {
            System.out.println("Who do you want to create?\n" +
                    "1 - Add new Cat\n" +
                    "2 - Add new Wolf\n" +
                    "3 - Add new Raven\n" +
                    "0 - Back");
            String num = scan.nextLine();
            switch (num) {
                case "1":
                    animal = HouseCat.createCat();
                    animal.setAnimalDeadListener(this);
                    return animal;
                case "2":
                    animal = ForestWolf.createWolf();
                    animal.setAnimalDeadListener(this);
                    return animal;
                case "3":
                    animal = Raven.createRaven();
                    animal.setAnimalDeadListener(this);
                    return animal;
                case "0":
                    return null;
                default:
                    System.out.println("What? I don't understand you. Try again");
                    break;
            }
        }
    }

    public void removeAnimal() {
        while (true) {
            System.out.println("Who do you want to remove?");
            System.out.println("Enter the animal number from 1 to " + predatorCage.getAnimalsCounter() + " or 0 to return");
            int num = scan.nextInt();
            scan.nextLine();
            if (num > 0 && predatorCage.removeAnimal(num - 1)) {
                System.out.println("Animal removed");
                return;
            } else if (num == 0) {
                return;
            } else
                System.out.println("Wrong number cage");
        }
    }

    private void removeAnimal(Animal animal) {
        for (int i = 0; i < predatorCage.getAnimalsCounter(); i++) {
            if (predatorCage.getAnimals().get(i).equals(animal)) {
                predatorCage.removeAnimal(i);
                return;
            }
        }
    }

    @Override
    public void onAnimalDead(Animal animal) {
        System.out.println(animal.toString() + " is dead. Why?");
        removeAnimal(animal);
    }
}

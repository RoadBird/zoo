package main;

import animals.Animal;
import animals.ForestWolf;
import animals.HouseCat;
import equipments.ExtensibleCage;
import interfases.Soundable;

import java.util.Map;
import java.util.Scanner;

public class Main implements Animal.IAnimalDeadListener {
    public static final Scanner scan = new Scanner(System.in);
    private final ExtensibleCage cage = new ExtensibleCage();

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
                        cage.addAnimal(animal);
                    }
                    break;
                case "4":
                    jumpAll();
                    break;
                case "5":
                    removeAnimal();
                    break;
                case "6":
                    makeSound(cage.getAnimals());
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
        new Main();
        scan.close();
    }

    public void showAnimalInfo() {
        if (cage.getAnimalsCounter() < 1) {
            System.out.println("Cage is empty");
            return;
        } else {
            double n;
            for (int i = 0; i < cage.getAnimalsCounter(); i++) {
                n = cage.getAnimals()[i].getFill();
                if (n > 0)
                    System.out.println(cage.getAnimals()[i].toString() +
                            ", size - " + cage.getAnimals()[i].getSize() + ", fill - " + n);
                else --i;
            }
        }
    }

    public void makeSound(Soundable[] sounds) {
        if (cage.getAnimalsCounter() < 1) {
            System.out.println("Cage is empty");
            return;
        } else for (Soundable sound : sounds) {
            if (sound != null) sound.sound();
        }
    }

    public void jumpAll() {
        if (cage.getAnimalsCounter() > 0) {
            double tmp = 0;
            int t = 0;
            for (int i = 0; i < cage.getAnimalsCounter(); i++) {
                if (cage.getAnimals()[i].jump() > tmp) {
                    tmp = cage.getAnimals()[i].jump();
                    t = i;
                }
            }
            System.out.println(cage.getAnimals()[t].toString() + " has best jump");
        } else
            System.out.println("None in cage");
    }

    public void feedAnimal() {
        while (true) {
            if (cage.getAnimalsCounter() < 1) {
                System.out.println("Cage is empty");
                return;
            } else {
                System.out.println("Enter the animal number from 1 to " + cage.getAnimalsCounter() + " or 0 to return");
                int num = scan.nextInt();
                scan.nextLine();
                if (num == 0)
                    return;
                else if (num > 0 && num <= cage.getAnimalsCounter()) {
                    System.out.println(cage.getAnimals()[num - 1].toString() +
                            " have new fill is " + cage.getAnimals()[num - 1].feed(cage.getAnimals()[num - 1].getFill() / 2));
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
            System.out.println("Enter the animal number from 1 to " + cage.getAnimalsCounter() + " or 0 to return");
            int num = scan.nextInt();
            scan.nextLine();
            if (num > 0 && cage.removeAnimal(num)) {
                System.out.println("Animal removed");
                return;
            } else if (num == 0) {
                return;
            } else
                System.out.println("Wrong number cage");
        }
    }

    private void removeAnimal(Animal animal) {
        for (int i = 0; i < cage.getAnimalsCounter(); i++) {
            if (cage.getAnimals()[i].equals(animal))
                cage.removeAnimal(i + 1);
        }
    }

    @Override
    public void onAnimalDead(Animal animal) {
        System.out.println(animal.toString() + " is dead. Why?");
        removeAnimal(animal);
    }
}

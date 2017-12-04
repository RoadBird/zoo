package main;

import animals.Animal;
import animals.ForestWolf;
import animals.HouseCat;
import animals.RoboCat;
import equipments.ExtensibleCage;
import interfases.Jumpable;
import interfases.Soundable;

import java.util.Scanner;

public class Main {
    static final Scanner scan = new Scanner(System.in);
    private final ExtensibleCage cage = new ExtensibleCage();

    public Main() {
        System.out.println("Yep, Hello!");
        boolean exit = false;
        do {
            System.out.println("Do you want to add new Animal?\n" +
                    "1 - Yes, add new Cat\n" +
                    "2 - Yes, add new Wolf\n" +
                    "3 - No, just touch the cage\n" +
                    "4 - No, just look at all\n" +
                    "0 - No, enough");
            int answer = scan.nextInt();
            switch (answer) {
                case 1:
                    cage.addAnimal(createCat(scan));
                    break;
                case 2:
                    cage.addAnimal(createWolf(scan));
                    break;
                case 3:
                    makeSound(cage.getAnimals());
                    break;
                case 4:
                    displayAnimals(cage.getAnimals());
                    break;
                case 0:
                    System.out.println("Thanks!");
                    exit = true;
                    break;
                default:
                    System.out.println("What? I don't understand you. Try again");
                    break;
            }
        } while (!exit);
        jumpAll(cage.getAnimals());
        System.out.println("Exit!");
    }

    public static void main(String[] args) {
        new Main();
        scan.close();
    }

    public void displayAnimals(Object[] animals) {
        for (int i = 0; i < animals.length; i++) {
            if (animals[i] != null) System.out.println("№" + i + " " + animals[i].toString());
        }
    }

    public void makeSound(Soundable[] sounds) {
        for (Soundable sound : sounds) {
            if (sound != null) sound.sound();
        }
    }

    public void jumpAll(Jumpable[] animals) {
        if (animals.length > 0) {
            double tmp = 0;
            int t = 0;
            for (int i = 0; i < animals.length; i++) {
                if (animals[i] != null && animals[i].jump() > tmp) {
                    tmp = animals[i].jump();
                    t = i;
                }
            }
            System.out.println(animals[t].toString() + " has best jump");
        }
    }

    public HouseCat createCat(Scanner scan) {
        System.out.println("Enter size of the new Cat");
        double size = scan.nextDouble();
        scan.nextLine();
        System.out.println("And his Name, please");
        return new HouseCat(size, scan.nextLine());
    }

    public ForestWolf createWolf(Scanner scan) {
        System.out.println("Enter size of the new Wolf");
        double size = scan.nextDouble();
        scan.nextLine();
        System.out.println("And his Name, please");
        return new ForestWolf(size, scan.nextLine());
    }
}

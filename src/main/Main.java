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
            System.out.println("What do you want?\n" +
                    "1 - Look at all in the Cage\n" +
                    "2 - Feed everyone\n" +
                    "3 - Add new Animal\n" +
                    "4 - Let them jump\n" +
                    "5 - Touch the cage\n" +
                    "0 - Exit");
            int answer = scan.nextInt();
            switch (answer) {
                case 1:
                    showAnimalInfo();
                    break;
                case 2:
                    feed(cage.getAnimals());
                    break;
                case 3:
                    Animal animal = createAnimal();
                    if (animal != null) cage.addAnimal(animal);
                    break;
                case 4:
                    jumpAll();
                    break;
                case 5:
                    makeSound(cage.getAnimals());
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
        System.out.println("Exit!");
    }

    public static void main(String[] args) {
        new Main();
        scan.close();
    }

    public void showAnimalInfo() {
        for (int i = 0; i < cage.getAnimals().length; i++) {
            if (cage.getAnimals()[i] != null)
                System.out.println(cage.getAnimals()[i].toString() +
                        ", size - " + cage.getAnimals()[i].getSize() + ", fill - " + cage.getAnimals()[i].getFill());
        }
    }

    public void makeSound(Soundable[] sounds) {
        for (Soundable sound : sounds) {
            if (sound != null) sound.sound();
        }
    }

    public void jumpAll() {
        if (cage.getAnimals().length > 0) {
            double tmp = 0;
            int t = 0;
            for (int i = 0; i < cage.getAnimals().length; i++) {
                if (cage.getAnimals()[i] != null && cage.getAnimals()[i].jump() > tmp) {
                    tmp = cage.getAnimals()[i].jump();
                    t = i;
                }
            }
            System.out.println(cage.getAnimals()[t].toString() + " has best jump");
        } else
            System.out.println("None in cage");
    }

//    public void checkFeel(Animal[] animals){
//        for(Animal animal : animals){
//            if(animal != null) System.out.println(animal.toString() + "'s feel is " + animal.getFill());
//        }
//    }

    public HouseCat createCat() {
        System.out.println("Enter size of the new Cat");
        double size = scan.nextDouble();
        scan.nextLine();
        System.out.println("And his Name, please");
        return new HouseCat(size, scan.nextLine());
    }

    public ForestWolf createWolf() {
        System.out.println("Enter size of the new Wolf");
        double size = scan.nextDouble();
        scan.nextLine();
        System.out.println("And his Name, please");
        return new ForestWolf(size, scan.nextLine());
    }

    public void feed(Animal[] animals) {
        for (Animal animal : animals) {
            if (animal != null) animal.setFill(animal.getFill() + animal.getSize() / 2);
        }
    }

    public Animal createAnimal() {
        Animal animal = null;
        boolean exit = false;
        while (!exit) {
            System.out.println("Who do you want to create?\n" +
                    "1 - Add new Cat\n" +
                    "2 - Add new Wolf\n" +
                    "0 - Back");
            int num = scan.nextInt();
            scan.nextLine();
            switch (num) {
                case 1:
                    animal = createCat();
                    exit = true;
                    break;
                case 2:
                    animal = createWolf();
                    exit = true;
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("What? I don't understand you. Try again");
                    break;
            }
        }
        return animal;
    }
}

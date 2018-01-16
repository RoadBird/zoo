package main;

import animals.*;
import equipments.ExtensibleCage;
import error.AnimalCreationException;
import input.FileImporter;
import input.Input;
import interfases.Soundable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static final Scanner scan = new Scanner(System.in);
    private Map<String, ExtensibleCage<? extends Animal>> cages = new HashMap<>();
    private Map<Integer, ExtensibleCage<? extends Animal>> cagesByNumber = new HashMap<>();

    public Main() {
        System.out.println("Yep, Hello!");
        cages.put(Mammals.class.getSimpleName(), new ExtensibleCage<Mammals>(Mammals.class.getSimpleName()));
        cages.put(Bird.class.getSimpleName(), new ExtensibleCage<Bird>(Bird.class.getSimpleName()));
        cages.put(Herbivore.class.getSimpleName(), new ExtensibleCage<Herbivore>(Herbivore.class.getSimpleName()));

        int i = 0;
        for (ExtensibleCage<? extends Animal> cage : cages.values()) {
            cagesByNumber.put(++i, cage);
        }

        boolean exit = false;
        do {
            System.out.println("What do you want?\n" +
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
                    addAnimalInCage();
                    break;
                case "4":
                    jumpAll();
                    break;
                case "5":
                    removeAnimal();
                    break;
                case "6":
                    makeSound();
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
       // new Main();
        List<Animal> list = FileImporter.importFromCSVFile("animals.csv");
        for(Animal a : list)
            System.out.println(a.toString());
        scan.close();
    }

    public void showAnimalInfo() {
        StringBuilder stringBuilder = new StringBuilder("\nWho do we have:\n");
        for (String cage : cages.keySet()) {
            stringBuilder.append("\tCage of ").append(cage).append(":\n");
            if (cages.get(cage).getAnimalsCounter() == 0) {
                stringBuilder.append("Cage is empty\n");
            } else {
                stringBuilder.append(cages.get(cage).getCageInfo()).append("\n");
            }
        }
        System.out.println(stringBuilder.toString());
    }

    public void makeSound() {
        ExtensibleCage<?> cage = chooseCage();
        if (cage == null)
            return;
        if (cage.getAnimalsCounter() < 1) {
            System.out.println("Cage is empty");
            return;
        }
        for (Soundable sound : cage.getAnimals()) {
            if (sound != null) sound.sound();
        }
    }

    public void jumpAll() {
        ExtensibleCage<?> cage = chooseCage();
        if (cage == null)
            return;
        if (cage.getAnimalsCounter() < 1) {
            System.out.println("Cage is empty");
            return;
        }
        if (cage.getAnimalsCounter() > 0) {
            double tmp = 0;
            int t = 0;
            for (int i = 0; i < cage.getAnimalsCounter(); i++) {
                if (cage.getAnimals().get(i).jump() > tmp) {
                    tmp = cage.getAnimals().get(i).jump();
                    t = i;
                }
            }
            System.out.println(cage.getAnimals().get(t).toString() + " has best jump");
        } else
            System.out.println("None in cage");
    }

    public void feedAnimal() {
        while (true) {
            ExtensibleCage<?> cage = chooseCage();
            if (cage == null)
                return;
            if (cage.getAnimalsCounter() < 1) {
                System.out.println("Cage is empty");
                return;
            } else {
                double num = Input.megaInputNumber("Enter the animal number from 1 to " + cage.getAnimalsCounter() + " or 0 to return");
                if (num == 0)
                    continue;
                else if (num > 0 && num <= cage.getAnimalsCounter()) {
                    System.out.println(cage.getAnimals().get((int) num - 1).toString() +
                            " have new fill is " + cage.getAnimals().get((int) num - 1).feed(50));
                    continue;
                } else
                    System.out.println("Wrong number of animal");
            }
        }
    }

    public void addAnimalInCage() {
        Animal animal = null;
        try {
            animal = createAnimal();
        } catch (AnimalCreationException e){
            System.out.println(e.getMessage());
        }
        if (animal != null) {
            if (animal instanceof Mammals) {
                if (animal instanceof Herbivore && Math.random() >= 0.5) {
                    cages.get(Herbivore.class.getSimpleName()).addAnimal(animal);
                } else {
                    cages.get(Mammals.class.getSimpleName()).addAnimal(animal);
                    cages.get(Mammals.class.getSimpleName()).checkHuntCondition(animal);
                }
            } else if (animal instanceof Bird) {
                cages.get(Bird.class.getSimpleName()).addAnimal(animal);
            }
        }
    }

    public Animal createAnimal() throws AnimalCreationException {
        Animal animal;
        while (true) {
            System.out.println("Who do you want to create ?\n" +
                    "1 - Add new Cat\n" +
                    "2 - Add new Wolf\n" +
                    "3 - Add new Raven\n" +
                    "4 - Add new Rabbit\n" +
                    "0 - Back");
            String num = scan.nextLine();
            switch (num) {
                case "1":
                    animal = HouseCat.createCat();
                    return animal;
                case "2":
                    animal = ForestWolf.createWolf();
                    return animal;
                case "3":
                    animal = Raven.createRaven();
                    return animal;
                case "4":
                    animal = Rabbit.createRabbit();
                    return animal;
                case "0":
                    return null;
                default:
                    System.out.println("What? I don't understand you. Try again");
                    break;
            }
        }
    }

    public ExtensibleCage<? extends Animal> chooseCage() {
        System.out.println("Enter the number of cage:\n");
        for (int n : cagesByNumber.keySet()) {
            System.out.println(n + " - Cage of " + cagesByNumber.get(n).getType());
        }
        System.out.println("... or something else to return");
        return cagesByNumber.get(Integer.decode(scan.nextLine()));
    }

    private void removeAnimal() {
        while (true) {
            ExtensibleCage<? extends Animal> cage = chooseCage();
            if (cage == null)
                return;
            if (cage.getAnimalsCounter() < 1) {
                System.out.println("Cage is empty");
                return;
            }
            double num = Input.megaInputNumber("Enter the animal number from 1 to " + cage.getAnimalsCounter() + " or 0 to return");
            if (num > 0 && cage.removeAnimal((int) num - 1)) {
                System.out.println("Animal removed");
            } else if (num == 0) {
            } else
                System.out.println("Wrong number of animal");
        }
    }
}

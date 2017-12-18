package main;

import animals.*;
import equipments.ExtensibleCage;
import interfases.Soundable;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static final Scanner scan = new Scanner(System.in);
    private Map<String, ExtensibleCage<? extends Animal>> cages = new HashMap<>();
    public Main() {
        System.out.println("Yep, Hello!");
        cages.put(Mammals.class.getSimpleName(), new ExtensibleCage<Mammals>());
        cages.put(Bird.class.getSimpleName(), new ExtensibleCage<Bird>());
        cages.put(Herbivore.class.getSimpleName(), new ExtensibleCage<Herbivore>());

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
        new Main();
        scan.close();
    }

    public void showAnimalInfo() {
        StringBuilder stringBuilder = new StringBuilder("\nWho do we have:\n");

        for(String cage : cages.keySet()){
            stringBuilder.append("\tCage of ").append(cage).append(":\n");
            if (cages.get(cage).getAnimalsCounter() == 0) {
                stringBuilder.append("Cage is empty\n");
            } else {
                stringBuilder.append(animalInfo(cages.get(cage)));
            }
        }
        System.out.println(stringBuilder.toString());
    }

    public String animalInfo(ExtensibleCage<?> someCage) {
        StringBuilder stringBuilder = new StringBuilder();
        Animal animal;
        double check;
        for (int i = 0; i < someCage.getAnimalsCounter(); i++) {
            animal = someCage.getAnimals().get(i);
            if ((check = animal.getFill()) > 0) {
                stringBuilder.append("Animal ").append(animal.getType()).append(": ");
                stringBuilder.append(" name - ").append(animal.getNickName()).append(", ");
                stringBuilder.append("size - ").append(animal.getSize()).append(", ");
                stringBuilder.append("fill - ").append(check).append(".\n");
            } else {
                stringBuilder.append(animal.toString()).append(" is dead. Why?\n");
                i--;
            }
        }
        return stringBuilder.toString();
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
                System.out.println("Enter the animal number from 1 to " + cage.getAnimalsCounter() + " or 0 to return");
                int num = scan.nextInt();
                scan.nextLine();
                if (num == 0)
                    continue;
                    //return;
                else if (num > 0 && num <= cage.getAnimalsCounter()) {
                    System.out.println(cage.getAnimals().get(num - 1).toString() +
                            " have new fill is " + cage.getAnimals().get(num - 1).feed(50));
                    continue;
                    //return;
                } else
                    System.out.println("Wrong number cage");
            }
        }
    }

    public void addAnimalInCage() {

        ExtensibleCage<Mammals> mammalsExtensibleCagec = (ExtensibleCage<Mammals>) cages.get("Mammals");
        ExtensibleCage<Bird> birdExtensibleCagec = (ExtensibleCage<Bird>) cages.get("Bird");
        ExtensibleCage<Herbivore> herbivoreExtensibleCagec = (ExtensibleCage<Herbivore>) cages.get("Herbivore");


        Animal animal = createAnimal();
        if (animal != null) {
            if (animal instanceof Mammals) {
                if (animal instanceof Herbivore && Math.random() >= 0.5) {
                    //cages.get("Herbivore").addAnimal(animal);
                    herbivoreExtensibleCagec.addAnimal((Herbivore) animal);
                    cages.get("Herbivore").checkHuntCondition(animal);
                } else {
                    //cages.get("Mammals").addAnimal(animal);
                    mammalsExtensibleCagec.addAnimal((Mammals) animal);
                    cages.get("Mammals").checkHuntCondition(animal);
                }
            } else if (animal instanceof Bird) {
                //cages.get("Bird").addAnimal(animal);
                birdExtensibleCagec.addAnimal((Bird) animal);
                cages.get("Bird").checkHuntCondition(animal);
            }
        } else throw new RuntimeException("");
    }

    public Animal createAnimal() {
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
        while (true) {
            System.out.println("Enter the number of cage:\n" +
                    "1 - Cage of mammals\n" +
                    "2 - Cage of birds\n" +
                    "3 - Cage of herbivores\n" +
                    "0 - back");
            String num = scan.nextLine();
            switch (num) {
                case "1":
                    return cages.get("Mammals");
                case "2":
                    return cages.get("Bird");
                case "3":
                    return cages.get("Herbivore");
                case "0":
                    return null;
                default:
                    System.out.println("Wrong number of cage");
            }
        }
    }

    private void removeAnimal() {
        while (true) {
            ExtensibleCage<?> cage = chooseCage();
            if (cage == null)
                return;
            if (cage.getAnimalsCounter() < 1) {
                System.out.println("Cage is empty");
                return;
            }
            System.out.println("Enter the animal number from 1 to " + cage.getAnimalsCounter() + " or 0 to return");
            int num = scan.nextInt();
            scan.nextLine();
            if (num > 0 && cage.removeAnimal(num - 1)) {
                System.out.println("Animal removed");
                //return;
            } else if (num == 0) {
                //return;
            } else
                System.out.println("Wrong number of animal");
        }
    }


}

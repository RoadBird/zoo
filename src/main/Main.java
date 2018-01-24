package main;

import animals.*;
import equipments.ExtensibleCage;
import error.AnimalCreationException;
import input.CSVFileFilter;
import input.CSVSearcer;
import input.FileImporter;
import input.Input;
import interfases.Soundable;
import io.MyLogger;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.Logger;
import org.pmw.tinylog.writers.ConsoleWriter;
import org.pmw.tinylog.writers.FileWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

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
                    "7 - Import from file\n" +
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
                case "7":
                    readFromCSV();
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
//        Configurator.defaultConfig()
//                .writer(new FileWriter("log.txt"))
//                .level(Level.DEBUG)
//                .writer(new ConsoleWriter())
//                .activate();
//        MyLogger.log("Start");
//        Logger.info("Start");
        List<Student> studentList = new LinkedList<>();
        try {
            File inputFile = new File("students.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            Element element = doc.getDocumentElement();
            System.out.println("Root element : " + element.getNodeName());
            NodeList listNode = element.getElementsByTagName("Student");
            for (int i = 0; i < listNode.getLength(); i++) {
                System.out.println("Students found");
                Node nNode = listNode.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("Student name : "
                            + eElement.getAttribute("name")
                            + ", student age : " + eElement.getAttribute("age"));
                    if(eElement.getAttribute("name").length() != 0 && eElement.getAttribute("age").length() != 0){
                        studentList.add(new Student(eElement.getAttribute("name"), eElement.getAttribute("age")));
                    }
                }
            }
            for (Student s : studentList){
                System.out.println(s);
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException | IOException e) {
            System.out.println(e.getMessage());
        }
        new Main();
        scan.close();
    }

    private static class Student{
        String name;
        String age;

        public Student(String name, String age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", age='" + age + '\'' +
                    '}';
        }
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
                } else
                    System.out.println("Wrong number of animal");
            }
        }
    }

    public void addAnimalInCage() {
        Animal animal = null;
        try {
            animal = createAnimal();
        } catch (AnimalCreationException e) {
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

    private void readFromCSV() {
        System.out.println("Enter the directory for search");
        String s = scan.nextLine();
        File file;
        if (s.equals(""))
            file = new File(".");
        else file = new File(s);
        if (!file.exists() || !file.isDirectory()) {
            System.out.println("This directory is not found");
            return;
        }
        CSVSearcer searcher = new CSVFileFilter();
        List<File> listFile = searcher.searchCSV(file);
        if (listFile.size() < 1) {
            System.out.println("There is not any CSV-file");
            return;
        }
        while (true) {
            System.out.println("Choose a file");
            for (int i = 0; i < listFile.size(); i++) {
                try {
                    System.out.println(i + " - " + listFile.get(i).getCanonicalPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            int ans = (int) Input.megaInputNumber("Enter a number of file");
            if (ans < 0 || ans > listFile.size()) {
                System.out.println("Wrong number. Please, try again");
                continue;
            } else {
                List<Animal> list;
                try {
                    list = FileImporter.getAnimalsFromCSVFile(listFile.get(ans));
                    for (Animal a : list)
                        System.out.println(a.toString());
                    return;
                } catch (AnimalCreationException e) {
                    System.out.println(e.getMessage());
                    return;
                }
            }
        }
    }
}

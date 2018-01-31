package io;

import animals.Animal;
import animals.Bird;
import animals.Herbivore;
import animals.Mammals;
import equipments.ExtensibleCage;
import error.AnimalCreationException;
import error.XMLInputException;
import meta.FillRetention;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyXMLLoad {
    public static Map<String, ExtensibleCage<? extends Animal>> importFromFIle() throws XMLInputException {
        Map<String, ExtensibleCage<? extends Animal>> map = new HashMap<>();
        ExtensibleCage<? extends Animal> cage;
        Animal animal;
        int it = 0;
        File loadFile = new File(".autosave");
        if (!loadFile.exists()) {
            map = new HashMap<>();
            map.put(Mammals.class.getSimpleName(), new ExtensibleCage<Mammals>(Mammals.class.getSimpleName()));
            map.put(Bird.class.getSimpleName(), new ExtensibleCage<Bird>(Bird.class.getSimpleName()));
            map.put(Herbivore.class.getSimpleName(), new ExtensibleCage<Herbivore>(Herbivore.class.getSimpleName()));
            return map;
        }
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(loadFile);
            doc.getDocumentElement().normalize();
            NodeList nListCage = doc.getElementsByTagName("CAGE");
            for (int i = 0; i < nListCage.getLength(); i++) {
                Element eCage = (Element) nListCage.item(i);
                Attr type = eCage.getAttributeNode("type");
                switch (type.getValue()) {
                    case "Mammals": {
                        cage = new ExtensibleCage<Mammals>(type.getValue());
                        break;
                    }
                    case "Bird": {
                        cage = new ExtensibleCage<Bird>(type.getValue());
                        break;
                    }
                    case "Herbivore": {
                        cage = new ExtensibleCage<Herbivore>(type.getValue());
                        break;
                    }
                    default:
                        continue;
                }
                NodeList nListAnimal = nListCage.item(i).getChildNodes();
                for (int p = 0; p < nListAnimal.getLength(); p++) {
                    if (nListAnimal.item(p).getNodeName().equals("ANIMAL")) {
                        try {
                            Element eAnimal = (Element) nListAnimal.item(p);
                            Attr animalType = eAnimal.getAttributeNode("type");
                            Attr animalSize = eAnimal.getAttributeNode("size");
                            Attr animalFill = eAnimal.getAttributeNode("fill");
                            animal = Animal.createAnimal(animalType.getValue(),
                                    Double.parseDouble(animalSize.getValue()),
                                    nListAnimal.item(p).getTextContent());
                            if (animal.getClass().getAnnotation(FillRetention.class) != null)
                                animal.setFill(Animal.INITIAL_FILL);
                            else
                                animal.setFill(Double.parseDouble(animalFill.getValue()));
                            cage.addAnimal(animal);
                            it++;
                        } catch (AnimalCreationException e) {
                            e.getMessage();
                        }
                    }
                }
                map.put(type.getValue(), cage);
            }
            System.out.println("There was/were load " + it + " animal(s)");
            return map;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        throw new XMLInputException();
    }
}

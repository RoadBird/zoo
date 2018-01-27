package io;

import animals.Animal;
import equipments.ExtensibleCage;
import error.AnimalCreationException;
import error.XMLInputException;
import input.AnimalsCollection;
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
    public static Map<String, ExtensibleCage<? extends Animal>> importFromFIle() throws XMLInputException, AnimalCreationException {
        Map<String, ExtensibleCage<? extends Animal>> map = new HashMap<>();
        ExtensibleCage<? extends Animal> cage;
        Animal animal;
        int it = 0;
        File loadFile = new File(".autosave");
        if(!loadFile.exists())
            throw new XMLInputException();
        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(loadFile);
            doc.getDocumentElement().normalize();
            NodeList nListCage = doc.getElementsByTagName("CAGE");
            for(int i = 0; i < nListCage.getLength(); i++){
                NamedNodeMap nMapCage = nListCage.item(i).getAttributes();
                Attr type = (Attr) nMapCage.getNamedItem("type");
                if(type == null)
                    throw new XMLInputException("Type of cage is not valid");

                cage = new ExtensibleCage<>(type.getValue());
                NodeList nListAnimal = nListCage.item(i).getChildNodes();
                for(int p = 0; p < nListAnimal.getLength(); p++){
                    NamedNodeMap nMapAnimal = nListAnimal.item(p).getAttributes();
                    Attr animalType = (Attr) nMapAnimal.getNamedItem("type");
                    Attr animalSize = (Attr) nMapAnimal.getNamedItem("size");
                    Attr animalFill = (Attr) nMapAnimal.getNamedItem("fill");
                    animal = AnimalsCollection.createAnimal(animalType.getValue());
                    animal.setSize(Double.parseDouble(animalSize.getValue()));
                    animal.setFill(Double.parseDouble(animalFill.getValue()));
                    animal.setNickName(nListAnimal.item(p).getTextContent());
                    cage.addAnimal(animal);
                    it++;
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

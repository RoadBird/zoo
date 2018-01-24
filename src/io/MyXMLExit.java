package io;

import animals.Animal;
import equipments.ExtensibleCage;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;
import java.util.Map;

public class MyXMLExit {
    final static String ELEMENT_NAME_ROOT = "ZOO";
    final static String ELEMENT_NAME_CAGE = "CAGE";
    final static String ELEMENT_NAME_ANIMAL = "ANIMAL";
    final static String ATTRIBUTE_NAME_TYPE = "type";
    final static String ATTRIBUTE_NAME_SIZE = "size";
    final static String ATTRIBUTE_NAME_FILL = "fill";
    public static void exportToFile(Map<String, ExtensibleCage<? extends Animal>> map) {
        try {
            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement(ELEMENT_NAME_ROOT);
            doc.appendChild(rootElement);

            for(String key : map.keySet()){
                ExtensibleCage<? extends Animal> cage = map.get(key);
                Attr cageAttr = doc.createAttribute(ATTRIBUTE_NAME_TYPE);
                cageAttr.setValue(key);
                Element cageElement = doc.createElement(ELEMENT_NAME_CAGE);
                cageElement.setAttributeNode(cageAttr);
                rootElement.appendChild(cageElement);

                List<? extends Animal> animalList = cage.getAnimals();
                for(Animal animal : animalList){
                    Attr typeAnimal = doc.createAttribute(ATTRIBUTE_NAME_TYPE);
                    typeAnimal.setValue(animal.getType());
                    Attr sizeAnimal = doc.createAttribute(ATTRIBUTE_NAME_SIZE);
                    sizeAnimal.setValue(String.valueOf(animal.getSize()));
                    Attr fillAnimal = doc.createAttribute(ATTRIBUTE_NAME_FILL);
                    fillAnimal.setValue(String.valueOf(animal.getFill()));
                    Element animalElement = doc.createElement(ELEMENT_NAME_ANIMAL);
                    animalElement.setAttributeNode(fillAnimal);
                    animalElement.setAttributeNode(sizeAnimal);
                    animalElement.setAttributeNode(typeAnimal);
                    animalElement.appendChild(doc.createTextNode(animal.getNickName()));
                    cageElement.appendChild(animalElement);
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(".autosave"));
            transformer.transform(source, result);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}

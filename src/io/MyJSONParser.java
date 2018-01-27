package io;

import animals.Animal;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import equipments.ExtensibleCage;
import error.AnimalCreationException;
import input.AnimalsCollection;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MyJSONParser {
    static ObjectMapper mapper = new ObjectMapper();
    public static void saveInFile(Object ob) {
        try {
            mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            StringWriter stringEmp = new StringWriter();
            mapper.writeValue(stringEmp, ob);
            System.out.println(stringEmp);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Map<String, ExtensibleCage<? extends Animal>> loadFromFile() throws AnimalCreationException {
        Map<String, ExtensibleCage<? extends Animal>> map = new HashMap<>();
        ExtensibleCage<? extends Animal> animalCage;
        Animal animal;
        try {
            JsonNode rootNode = mapper.readTree(new File("save.json"));
            Iterator<JsonNode> cages = rootNode.elements();
            while(cages.hasNext()){
                JsonNode cage = cages.next();
                animalCage = new ExtensibleCage<>(cage.path("type").asText());
                Iterator<JsonNode> animals = cage.path("animals").elements();
                while (animals.hasNext()){
                    JsonNode animalNode = animals.next();
                    animal = AnimalsCollection.createAnimal(animalNode.path("type").asText());
                    animal.setSize(animalNode.path("size").asDouble());
                    animal.setNickName(animalNode.path("nickName").asText());
                    animal.setFill(animalNode.path("fill").asDouble());
                    animalCage.addAnimal(animal);
                }
                map.put(cage.path("type").asText(), animalCage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}

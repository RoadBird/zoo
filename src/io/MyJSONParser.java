package io;

import animals.Animal;
import animals.Bird;
import animals.Herbivore;
import animals.Mammals;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import equipments.ExtensibleCage;
import error.AnimalCreationException;
import meta.FillRetention;

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, ExtensibleCage<? extends Animal>> loadFromFile() throws AnimalCreationException {
        Map<String, ExtensibleCage<? extends Animal>> map = new HashMap<>();
        ExtensibleCage<? extends Animal> animalCage;
        Animal animal;
        File file = new File("save.json");
        if (!file.exists()) {
            map = new HashMap<>();
            map.put(Mammals.class.getSimpleName(), new ExtensibleCage<Mammals>(Mammals.class.getSimpleName()));
            map.put(Bird.class.getSimpleName(), new ExtensibleCage<Bird>(Bird.class.getSimpleName()));
            map.put(Herbivore.class.getSimpleName(), new ExtensibleCage<Herbivore>(Herbivore.class.getSimpleName()));
            return map;
        }
        try {
            JsonNode rootNode = mapper.readTree(file);
            Iterator<JsonNode> cages = rootNode.elements();
            while (cages.hasNext()) {
                JsonNode cage = cages.next();
                animalCage = new ExtensibleCage<>(cage.path("type").asText());
                Iterator<JsonNode> animals = cage.path("animals").elements();
                while (animals.hasNext()) {
                    JsonNode animalNode = animals.next();
                    animal = Animal.createAnimal(animalNode.path("type").asText(),
                            animalNode.path("size").asDouble(),
                            animalNode.path("nickName").asText());
                    if (animal.getClass().getAnnotation(FillRetention.class) != null)
                        animal.setFill(Animal.INITIAL_FILL);
                    else
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

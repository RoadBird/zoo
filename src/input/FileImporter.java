package input;

import animals.*;
import error.AnimalCreationException;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FileImporter {
    public static List<Animal> importFromCSVFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("File not exists");
            return null;
        } else System.out.println("File OK");
        List<String[]> listValue = getValueFromCSV(file);
        if(listValue == null){
            System.out.println("Error of read file");
            return null;
        }
        List<Animal> list = new LinkedList<>();
        Animal animal;
        for(String[] s : listValue){
            try {
                animal = AnimalsCollection.createAnimal(s[0]);
                animal.setNickName(s[1]);
                animal.setSize(Double.parseDouble(s[2]));
                list.add(animal);
            } catch (AnimalCreationException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    private static List<String[]> getValueFromCSV(File file) {
        BufferedReader bf = null;
        try {
            bf = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            List<String[]> list = new ArrayList<>();
            int temp = bf.read();
            while (temp != -1) {
                while (temp != 10 && temp != -1) {
                    sb.append((char) temp);
                    temp = bf.read();
                }
                list.add(sb.toString().split(","));
                sb.setLength(0);
                temp = bf.read();
            }
            return list;
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            try {
                bf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

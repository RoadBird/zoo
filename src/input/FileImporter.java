package input;

import animals.*;
import error.AnimalCreationException;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FileImporter implements CSVSearcer {
    public List<File> listFile = new LinkedList<>();
    public static List<Animal> getAnimalsFromCSVFile(File file) throws AnimalCreationException {
        if (!file.exists()) {
            throw new AnimalCreationException("File not exist");
        }
        List<String[]> listValue = getValueFromCSV(file);
        List<Animal> list = new LinkedList<>();
        if(listValue.size() == 0){
            throw new AnimalCreationException("File is empty");
        }
        Animal animal;
        for (String[] s : listValue) {
            animal = Animal.createAnimal(s[0], Double.parseDouble(s[2]), s[1]);
            list.add(animal);
        }
        return list;
    }

    private static List<String[]> getValueFromCSV(File file) throws AnimalCreationException{
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
            System.out.println(e.getMessage());
            throw new AnimalCreationException("File not found");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new AnimalCreationException("Exception of read file");
        } finally {
            try {
                bf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public List<File> searchCSV(File start) {
        File[] files = start.listFiles();
        for (File f : files) {
            if (f.isDirectory())
                searchCSV(f);
            else if (f.getName().endsWith(".csv")) {
                listFile.add(f);
            }
        }
        return listFile;
    }
}

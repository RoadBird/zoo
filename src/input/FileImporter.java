package input;

import animals.*;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FileImporter {
    public static List<Animal> importFromFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("File not exists");
            return null;
        } else System.out.println("File OK");
        List<Animal> list = new LinkedList<>();
        List<String> listValue = getValueFromCSV(file);
        if(listValue == null){
            System.out.println("Error of read file");
            return null;
        }
        for(int i = 0; i < listValue.size()/3; i+=3){
            if(listValue.get(i).equals("Cat"))
                list.add(new HouseCat(Double.parseDouble(listValue.get(i+1)), listValue.get(i+2)));
            else if (listValue.get(i).equals("Wolf"))
                list.add(new ForestWolf(Double.parseDouble(listValue.get(i+1)), listValue.get(i+2)));
            else if (listValue.get(i).equals("Rabbit"))
                list.add(new Rabbit(Double.parseDouble(listValue.get(i+1)), listValue.get(i+2)));
            else if (listValue.get(i).equals("Raven"))
                list.add(new Raven(Double.parseDouble(listValue.get(i+1)), listValue.get(i+2)));
            else System.out.println("Error of animal's type");
        }
        return list;
    }

    private static List<String> getValueFromCSV(File file) {
        try {
            BufferedReader bf = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            List<String> list = new ArrayList<>();
            int temp;
            while ((temp = bf.read()) != -1) {
                while (temp != ',')
                    sb.append((char) temp);
                list.add(sb.toString());
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }
}

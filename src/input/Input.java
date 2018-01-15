package input;

import animals.Animal;
import main.Main;

import java.io.*;
import java.util.List;

public class Input {
    public static double megaInputNumber(String message){
        System.out.println(message);
        while (true){
            String str = Main.scan.nextLine();
            try {
                return Double.parseDouble(str.replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("You must enter some number");
            }
        }
    }
    public static String inputFromConsole(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int temp;
        try {
            while ((temp = br.read()) != -1 && temp != 10) {
                sb.append((char)temp);
            }
        } catch (IOException e){
            System.out.println(e);
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        return sb.toString();
    }
    public static List<Animal> importFromFile(){
        File file = new File("animals.csv");
        if(!file.exists()){
            System.out.println("File not exists");
        } else System.out.println("File OK");
        try {
            BufferedReader bf = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            int temp = 0;
            while((temp = bf.read()) != -1){
                sb.append((char)temp);
            }
            System.out.println(sb.toString());
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e){
            System.out.println(e);
        }
        return null;
    }
}

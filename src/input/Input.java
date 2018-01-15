package input;

import main.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
}

package main;

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
}

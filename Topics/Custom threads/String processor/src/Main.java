import java.util.Arrays;
import java.util.Scanner;

class StringProcessor extends Thread {

    final Scanner scanner = new Scanner(System.in); // use it to read string from the standard input

    @Override
    public void run() {
        while (scanner.hasNext()) {
            String word = scanner.nextLine();
            if (word.equals(word.toUpperCase())) {
                System.out.println("FINISHED");
                return;
            } else {
                System.out.println(word.toUpperCase());
            }
        }
    }
}
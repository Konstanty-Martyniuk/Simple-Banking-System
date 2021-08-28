import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int firstHours = Integer.valueOf(scanner.nextLine()) * 3600;
        int firstMinutes = Integer.valueOf(scanner.nextLine()) * 60;
        int firstSeconds = Integer.valueOf(scanner.nextLine());
        int secondHours = Integer.valueOf(scanner.nextLine()) * 3600;
        int secondMinutes = Integer.valueOf(scanner.nextLine()) * 60;
        int secondSeconds = Integer.valueOf(scanner.nextLine());

        System.out.println(secondHours + secondMinutes + secondSeconds
            - firstHours - firstMinutes - firstSeconds);
    }
}
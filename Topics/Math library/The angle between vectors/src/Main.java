import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        int a = Integer.valueOf(scanner.next());
        int b = Integer.valueOf(scanner.next());
        int c = Integer.valueOf(scanner.next());
        int d = Integer.valueOf(scanner.next());

        double angle = Math.abs(Math.atan2(b, a) - Math.atan2(d, c));

        System.out.println(Math.toDegrees(angle));
    }
}
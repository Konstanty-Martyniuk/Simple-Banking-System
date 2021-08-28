import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double result1 = 0.0;
        double result2 = 0.0;
        double a = scanner.nextDouble();
        double b = scanner.nextDouble();
        double c = scanner.nextDouble();

        double d = Math.sqrt(b * b - 4 * a * c);

        result1 = (-b + d) / (2 * a);
        result2 = (-b - d) / (2 * a);
        if (result1 > result2) {
            System.out.println(result2 + " " + result1);
        } else {
            System.out.println(result1 + " " + result2);
        }

    }
}
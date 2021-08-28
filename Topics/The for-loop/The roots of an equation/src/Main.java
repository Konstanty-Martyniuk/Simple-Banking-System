import java.util.Scanner;
class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        int b = sc.nextInt();
        int c = sc.nextInt();
        int d = sc.nextInt();

        int temp;
        for (int x = 0; x <= 1000; x++) {
            temp = a * x * x * x + b * x * x + c * x + d;
            if (temp == 0) {
                System.out.println(x);
            }
        }
    }
}
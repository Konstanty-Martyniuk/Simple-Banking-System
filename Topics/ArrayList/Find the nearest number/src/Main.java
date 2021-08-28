import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> result = new ArrayList<>();
        int distance = Integer.MAX_VALUE;
        ArrayList<Integer> numbers = new ArrayList<>();
        for (String val: scanner.nextLine().split(" ")) {
            numbers.add(Integer.valueOf(val));
        }

        int value = Integer.valueOf(scanner.nextLine());

        for (int num: numbers) {
            int tempDistance = Math.abs(value - num);
            if (tempDistance < distance) {
                distance = tempDistance;
            }
        }

        for (int num: numbers) {
            if (Math.abs(value - num) == distance) {
                result.add(num);
            }
        }

        Collections.sort(result);
        result.forEach(e -> System.out.print(e + " "));


    }
}
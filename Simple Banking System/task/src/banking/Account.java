package banking;

import java.util.Random;

public class Account {
    private final String accountNumber;
    private String pin;
    private double balance;
    private boolean logged;
    Random random = new Random();

    public Account() {
        this.accountNumber = generateAccNumber();
        this.pin = String.format("%04d", random.nextInt(10000));
        this.balance = 0;
        this.logged = false;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance += balance;
    }

    private String generateAccNumber() {
        boolean isNotCorrectNumber = true;
        StringBuilder sb = new StringBuilder();
        do {
            sb.setLength(0);
            sb.append(400000);
            for (int i = 0; i < 10; i++) {
                sb.append(random.nextInt(9));
            }
            isNotCorrectNumber = !isCorrectNumber(sb.toString());
        } while (isNotCorrectNumber);

        return sb.toString();
    }

    public static boolean isCorrectNumber(String accountNumber) {
        int sum = 0;

        for (int i = 0; i < accountNumber.length(); i++) {
            int digit = Character.getNumericValue(accountNumber.charAt(i));

            if (i % 2 == 0) {
                digit = digit * 2 > 9 ? digit * 2 - 9 : digit * 2;
                sum += digit;
                continue;
            }
            sum += digit;
        }
        return sum % 10 == 0;
    }
}

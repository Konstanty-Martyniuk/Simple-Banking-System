package banking;

public class Main {
    public static void main(String[] args) {
        Database.createDatabase(args);
        Bank bank = new Bank();
        bank.createBank();
    }
}
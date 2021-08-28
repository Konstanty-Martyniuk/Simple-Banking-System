package banking;

import java.sql.*;
import java.util.Scanner;

public class Bank {
    Scanner scanner = new Scanner(System.in);

    private final String SUCCESS = "Success!";
    private final String SUCCESS_LOGIN = "You have successfully logged in!";
    private final String FAIL_TO_LOGIN = "Wrong card number or PIN!";
    private final String LOG_OUT = "You have successfully logged out!";
    private final String NO_MONEY = "Not enough money!";
    private final String SAME_ACCOUNT_ERR = "You can't transfer money to the same account!";
    private final String WRONG_CARD_NUMBER = "Probably you made a mistake in the card number. Please try again!";
    private final String NO_SUCH_CARD = "Such a card does not exist.";
    private final String SUCCESS_INCOME = "Income was added!";
    private final String CLOSED = "The account has been closed!";
    private final String URL = "jdbc:sqlite:card.s3db";
    private final Connection connection = this.connect();
    private String tempAccNum = "";
    private boolean repeat = true;
    private boolean isLogged = false;

    void createBank() {
        while (repeat) {
            showBankUI();
        }
        System.out.println("Bye!");
    }

    void showBankUI() {
        System.out.println("1. Create an account" +
                "\n2. Log into account" +
                "\n0. Exit");

        int response = Integer.valueOf(scanner.nextLine());

        switch (response) {
            case 1:
                createAccount();
                break;
            case 2:
                logIntoAccount();
                break;
            case 0:
                repeat = false;
                return;
            default:
                System.out.println("Wrong number!");
        }
    }

    void showAccountUI() throws SQLException {
        System.out.println("1. Balance" +
                "\n2. Add income" +
                "\n3. Do transfer" +
                "\n4. Close account" +
                "\n5. Log out" +
                "\n0. Exit");

        int response = Integer.valueOf(scanner.nextLine());

        switch (response) {
            case 1:
                System.out.println("Balance:" + showBalance());
                break;
            case 2:
                System.out.println("Enter income:");
                int value = Integer.parseInt(scanner.nextLine());
                addIncome(tempAccNum, value);
                System.out.println(SUCCESS_INCOME);
                break;
            case 3:
                doTransfer();
                break;
            case 4:
                deleteAccount();
                logout();
                break;
            case 5:
                logout();
                System.out.println(LOG_OUT);
                return;
            case 0:
                logout();
                repeat = false;
                return;
        }
    }

    void logout() {
        isLogged = false;
        tempAccNum = "";
    }

    void createAccount() {
        Account account = new Account();

        System.out.println("Your card has been created " +
                "\nYour card number:");
        System.out.println(account.getAccountNumber());
        System.out.println("Your card PIN:");
        System.out.println(account.getPin());
        insert(account.getAccountNumber(), account.getPin(), account.getBalance());
    }

    void logIntoAccount() {
        System.out.println("Enter your card number:");
        String cardNumber = scanner.nextLine();
        System.out.println("Enter your PIN:");
        String pin = scanner.nextLine();
        checkAndLogin(cardNumber, pin);
    }

    double showBalance() {
        String query = "SELECT balance FROM card WHERE number =" + tempAccNum;
        double result = 0.0;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            result = rs.getDouble("balance");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    void insert(String number, String pin, double balance) {
        String query = "INSERT INTO card(number,pin,balance) VALUES(?,?,?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, number);
            pstmt.setString(2, pin);
            pstmt.setDouble(3, balance);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void checkAndLogin(String cardNumber, String cardPin) {
        String query = "SELECT id, number, pin FROM card WHERE number=" + cardNumber
                + " AND pin=" + cardPin;

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                isLogged = true;
                tempAccNum = cardNumber;
                System.out.println(SUCCESS_LOGIN);
                while (isLogged) {
                    showAccountUI();
                }
            } else {
                System.out.println(FAIL_TO_LOGIN);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void addIncome(String cardNumber, int value) {

        int tempId = getTempId(cardNumber);

        String query = "UPDATE card SET balance = balance + ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, value);
            pstmt.setInt(2, tempId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private int getTempId(String cardNumber) {
        int tempId = -1;
        String queryId = "SELECT id FROM card WHERE number=" + cardNumber;

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(queryId)) {
            tempId = rs.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tempId;
    }

    void doTransfer() {
        System.out.println("Transfer " +
                "\nEnter card number:");
        String recipientsCard = scanner.nextLine();
        if (tempAccNum.equals(recipientsCard)) {
            System.out.println(SAME_ACCOUNT_ERR);
        } else if (!Account.isCorrectNumber(recipientsCard)) {
            System.out.println(WRONG_CARD_NUMBER);
        } else if (isCardExists(recipientsCard)) {
            System.out.println("Enter how much money you want to transfer:");
            int amountToTransfer = Integer.parseInt(scanner.nextLine());
            if (isEnoughMoney(amountToTransfer)) {
                updateBalanceForSenderAndRecipient(tempAccNum, recipientsCard, amountToTransfer);
                System.out.println(SUCCESS);
            } else {
                System.out.println(NO_MONEY);
            }
        } else {
            System.out.println(NO_SUCH_CARD);
        }
    }

    boolean isCardExists(String recipientsCard) {
        String query = "SELECT number FROM card WHERE number =" + recipientsCard;

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    boolean isEnoughMoney(int amountToTransfer) {
        double customersMoney = showBalance();
        return customersMoney > amountToTransfer;
    }

    void updateBalanceForSenderAndRecipient(String senderCard, String recipientsCard, int value) {
        addIncome(recipientsCard, value);
        addIncome(senderCard, value * -1);
    }

    void deleteAccount() {
        int accId = getTempId(tempAccNum);
        String query = "DELETE FROM card WHERE id=" + accId;

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            System.out.println(CLOSED);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

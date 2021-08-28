package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    public static void createDatabase(String[] params) {
        String name = "noName";
        if ("-fileName".equals(params[0])) {
            name = params[1];
        }
        String url = "jdbc:sqlite:" + name;

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()){
            try (Statement statement = con.createStatement()){
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS card (" +
                        "id INTEGER PRIMARY KEY," +
                        "number TEXT NOT NULL," +
                        "pin TEXT NOT NULL," +
                        "balance INTEGER DEFAULT 0)");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
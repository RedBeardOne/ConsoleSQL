package data;

import java.sql.*;

public class DB_commands {
    Statement statement;

    public void createDB(String table) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
        statement = connection.createStatement();

        String str = String.format("CREATE TABLE %s (ID INTEGER, name VARCHAR(100), mail VARCHAR(100), age INTEGER)", table);
        System.out.println(str);
        statement.executeUpdate(str);
    }

    public void updateDB(String command) throws SQLException {
        statement.executeUpdate(command);
    }

    public void sortDB(String table) throws SQLException {
        String command = String.format("SELECT * FROM %s ORDER BY id asc", table);
        System.out.println(command);
        ResultSet cursor =  statement.executeQuery(command);
        while (cursor.next()) {
            System.out.print(" ID = " + cursor.getInt("ID"));
            System.out.print(" name = " + cursor.getString("name"));
            System.out.print(" mail = " + cursor.getString("mail"));
            System.out.print(" age = " + cursor.getInt("age") + "\n");
        }
    }

    public void selectFromDB(String table, String word) throws SQLException {
            String inquiry = "SELECT * FROM " + table + " WHERE name LIKE '%" + word + "%' OR mail LIKE '%" + word + "%' ";
            System.out.println(inquiry);
            ResultSet cursor = statement.executeQuery(inquiry);
            while (cursor.next()) {
                System.out.print(" ID = " + cursor.getInt("ID"));
                System.out.print(" name = " + cursor.getString("name"));
                System.out.print(" mail = " + cursor.getString("mail"));
                System.out.print(" age = " + cursor.getInt("age") + "\n");
            }
    }

    public void deleteDB(String table) throws SQLException {
        String inquiry = String.format("DROP TABLE %s", table);
        System.out.println(inquiry);
        statement.executeUpdate(inquiry);
        System.out.println("Need to create table first");

    }
}


package data;

import java.sql.*;

public class DB_commands {
    Statement statement;

    public void createDB(String table) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
        statement = connection.createStatement();

        String str = String.format("CREATE TABLE %s (ID INTEGER, name VARCHAR(100), mail VARCHAR(100), age INTEGER)", table);
        statement.executeUpdate(str);

    }

    public void updateDB(String table, int ID, String name, String mail, int age) throws SQLException {
        if (statement != null) {
            String str = String.format("INSERT INTO TABLE %s (%d INTEGER, %s VARCHAR(100), %s VARCHAR(100), %d INTEGER )",
                    table, ID, name, mail, age);
            statement.executeUpdate(str);
        } else {
            System.out.println("Need to create table first");
        }
    }

    public void sortDB(String table) throws SQLException {
        if (statement != null) {
            ResultSet cursor = statement.executeQuery(String.format("SELECT * FROM %s ORDER BY ACS", table));
            while (cursor.next()) {
                System.out.print(" ID = " + cursor.getInt("ID"));
                System.out.print(" name = " + cursor.getString("name"));
                System.out.print(" mail = " + cursor.getString("mail"));
                System.out.print(" age = " + cursor.getInt("age") + "\n");
            }
        } else {
            System.out.println("Need to create table first");
        }
    }


    public void selectFromDB(String table, String word) throws SQLException {
        if (statement != null) {
            String inquiry = String.format("SELECT * FROM " + table + " WHERE name LIKE '%" + word + "%' OR mail LIKE '%" + word + "%' ");
            ResultSet cursor = statement.executeQuery(inquiry);
            while (cursor.next()) {
                System.out.print(" ID = " + cursor.getInt("ID"));
                System.out.print(" name = " + cursor.getString("name"));
                System.out.print(" mail = " + cursor.getString("mail"));
                System.out.print(" age = " + cursor.getInt("age") + "\n");
            }
        } else {
            System.out.println("Need to create table first");
        }
    }

    public void deleteDB(String table) throws SQLException {
        if (statement != null) {
            String inquiry = String.format("DROP TABLE %s", table);
            statement.executeUpdate(inquiry);
        } else {
            System.out.println("Need to create table first");
        }
    }
}


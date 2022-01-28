package data.dao;

import data.source.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainFinal {
    public static void main(String[] args) {
        MainFinal.run();

    }

    private static void run() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db")) {
            sqlTask(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    private static void sqlTask(Connection connection) throws SQLException {
        AuthorDAO authorDAO = new AuthorDAO(connection);
        BookDAO bookDAO = new BookDAO(connection);
        UserDAO userDAO = new UserDAO(connection);
        ReviewDAO reviewDAO = new ReviewDAO(connection);

        authorDAO.createTable();
        bookDAO.createTable();
        userDAO.createTable();
        reviewDAO.createTable();

        Repository repo = new Repository(authorDAO,bookDAO,userDAO,reviewDAO);
    }
}

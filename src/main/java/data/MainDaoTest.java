package data;

import data.dao.AuthorDAO;
import data.dao.BookDAO;
import data.dao.ReviewDAO;
import data.dao.UserDAO;
import data.repository.Author;
import data.repository.Book;
import data.repository.Review;
import data.repository.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;

public class MainDaoTest {
    public static void main(String[] args) {

        new MainDaoTest().run();
    }


    private void run() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db")) {
            sqlTask(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void sqlTask(Connection connection) throws SQLException {
        AuthorDAO authorDAO = new AuthorDAO(connection);
        BookDAO bookDAO = new BookDAO(connection);
        UserDAO userDAO = new UserDAO(connection);
        ReviewDAO reviwDAO = new ReviewDAO(connection);

        authorDAO.createTable();
        bookDAO.createTable();
        userDAO.createTable();
        reviwDAO.createTable();

        Author great = new Author("Name Great", 1930);
        Author a_kid = new Author("Kid Author", 1991);
        Author third = new Author("Name Unknown", 1988);
        Author oneMore = new Author("Tolkin", 1990);
        Author lastOne = new Author("Me", 1500);
        authorDAO.insert(great);
        authorDAO.insert(a_kid);
        authorDAO.insert(third);
        authorDAO.insert(oneMore);
        authorDAO.insert(lastOne);

        Book bOne = new Book("Epic", great.getID());
        Book bTwo = new Book("Smile", a_kid.getID());
        Book three = new Book("About", third.getID());
        Book fourth = new Book("Power ring", oneMore.getID());
        Book secondTolkin = new Book("How to destroy it", oneMore.getID());
        Book lastTest = new Book("Procastination", lastOne.getID());
        bookDAO.insert(bOne);
        bookDAO.insert(bTwo);
        bookDAO.insert(three);
        bookDAO.insert(fourth);
        bookDAO.insert(secondTolkin);
        bookDAO.insert(lastTest);

        User Olya = new User("Olya");
        User Roma = new User("Roma");
        User Andrev = new User("Andrev");
        User Vasya = new User("Vasya");
        User Katya = new User("Katya");
        User Oleg = new User("Oleg");
        userDAO.insert(Olya);
        userDAO.insert(Andrev);
        userDAO.insert(Roma);
        userDAO.insert(Vasya);
        userDAO.insert(Katya);
        userDAO.insert(Oleg);

        Review review1 = new Review("Very goood", 1, 1);
        Review review2 = new Review("Omg", 2, 2);
        Review review3 = new Review("Boring", 3, 3);
        Review review4 = new Review("Sad", 4, 4);
        Review review5 = new Review("So funny", 5, 5);
        Review review6 = new Review("Impressive", 6, 6);
        Review review7 = new Review("Need to buy", 1, 1);
        Review review8 = new Review("repetable", 2, 1);
        reviwDAO.insert(review1);
        reviwDAO.insert(review2);
        reviwDAO.insert(review3);
        reviwDAO.insert(review4);
        reviwDAO.insert(review5);
        reviwDAO.insert(review6);
        reviwDAO.insert(review7);
        reviwDAO.insert(review8);

        Collection<Review> o = reviwDAO.getItemsByName("Ol");
        for (Review review : o) {
            System.out.println("result of " + review);
        }

        Book update = new Book(1, "Zamena", lastOne.getID());
        bookDAO.update(update);

//        authorDAO.deleteDB();
//        bookDAO.deleteDB();
//        userDAO.deleteDB();
//        reviwDAO.deleteDB();

    }

}

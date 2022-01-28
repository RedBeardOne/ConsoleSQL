package data;

import data.dao.*;
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
        ReviewDAO reviewDAO = new ReviewDAO(connection);

        authorDAO.createTable();
        bookDAO.createTable();
        userDAO.createTable();
        reviewDAO.createTable();

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
        reviewDAO.insert(review1);
        reviewDAO.insert(review2);
        reviewDAO.insert(review3);
        reviewDAO.insert(review4);
        reviewDAO.insert(review5);
        reviewDAO.insert(review6);
        reviewDAO.insert(review7);
        reviewDAO.insert(review8);
        System.out.println("TEST Of Author dao");
        System.out.println(authorDAO.getById(2).get());
        Collection<Author> authorD = authorDAO.getAll();
        for (Author author : authorD) {
            System.out.println(author);
        }
        System.out.println(authorDAO.getItemsByName("F"));
        System.out.println(authorDAO.getById(2).get());
        authorDAO.update(new Author(2, "Changes Second ID", 1000));
        System.out.println(authorDAO.getById(2).get());

        System.out.println("TEST Of Book dao");
        Collection<Book> allBook = bookDAO.getAll();
        for (Book book : allBook) {
            System.out.println(book);
        }
        System.out.println(bookDAO.getById(3));
        bookDAO.update(new Book(3, "50 shadow of  grey", 2));
        System.out.println(bookDAO.getById(3));
        System.out.println(bookDAO.getItemsByName("grey"));

        System.out.println("Test of User dao");
        Collection<User> allUser = userDAO.getAll();
        for (User user : allUser) {
            System.out.println(user);
        }
        System.out.println(userDAO.getById(6));
        userDAO.update(new User(6, "Fantomas"));
        System.out.println(userDAO.getById(6));
        System.out.println(userDAO.getItemsByName("Ty"));

        System.out.println("test of Review dao");
        Collection<Review> allReview = reviewDAO.getAll();
        for (Review review : allReview) {
            System.out.println(review);
        }
        System.out.println(reviewDAO.getById(3));
        reviewDAO.update(new Review(2, "changed commite", 3, 4));
        System.out.println(reviewDAO.getById(3));
        System.out.println(reviewDAO.getItemsByName("com"));
        ILibraryRepository iLib= authorDAO;
        iLib.deleteDB();
        iLib = bookDAO;
        iLib.deleteDB();
        iLib = userDAO;
        iLib.deleteDB();
        iLib =reviewDAO;
        iLib.deleteDB();
    }

}

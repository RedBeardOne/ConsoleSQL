package data;

import data.dao.AuthorDAO;
import data.repository.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MainDaoTestTest {

    private Connection create() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
        return connection;
    }

    @Test
    void sqlTask() throws SQLException {
        AuthorDAO authorDAO = new AuthorDAO(create());
        authorDAO.createTable();
        Author great = new Author("Name Great", 1930);
        authorDAO.insert(great);
        Author author = authorDAO.getById(1).get();
        Author toEqual = new Author(1, "Name Great", 1930);
        //assertEquals(toEqual, author);
    }
}
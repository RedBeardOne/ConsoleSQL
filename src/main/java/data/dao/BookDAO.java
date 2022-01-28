package data.dao;

import data.repository.Book;


import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class BookDAO implements ILibraryRepository<Book>{
    private final Connection connection;

    public BookDAO(Connection connection) {
        this.connection = connection;
    }
    @Override
    public void createTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS book (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "title VARCHAR(100), " +
                            "author_ID INTEGER)");
        }
    }
    @Override
    public void insert(Book book) throws SQLException {
        if (book.getId() != 0) {
            throw new IllegalArgumentException("ID is: " + book.getId());
        }
        if (book.getAuthorId() == 0) {
            throw new IllegalArgumentException("First you have to set author");
        }
        final String sql = "INSERT INTO book (title, author_ID) VALUES (?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setInt(2, book.getAuthorId());

            int affect = statement.executeUpdate();
            if (affect == 0) {
                throw new SQLException("Creating book failed, no row affect");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating book failed,something went wrong");
                }
            }
        }
    }
    @Override
    public void update(Book book) throws SQLException {
        if (book.getId() == 0) {
            throw new IllegalArgumentException("ID is not set");
        }
        final String sql = "UPDATE book " +
                "SET title =?, author_ID =?" +
                "WHERE id =?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setInt(2, book.getAuthorId());
            statement.setInt(3, book.getId());
            statement.executeUpdate();
        }
    }
    @Override
    public Collection<Book> getAll() throws SQLException {
        Collection<Book> collect = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet cursor = statement.executeQuery("SELECT * FROM book");
            while (cursor.next()) {
                collect.add(createBook(cursor));
            }
        }
        return collect;
    }
    @Override
    public Collection<Book> getAllItemsByIdOwner(int id) throws SQLException {
        String sql = "Select  book.ID, book.title, book.author_ID FROM book " +
                "JOIN author ON book.author_id = author.id " +
                "WHERE book.author_id = ?";
        Collection<Book> collect = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet cursor = statement.executeQuery();
            while (cursor.next()) {
                collect.add(createBook(cursor));
            }
        }
        return collect;
    }
    @Override
    public Collection<Book> getItemsByName(String word) throws SQLException {
        Collection<Book> collect = new ArrayList<>();
        String sql = "SELECT * FROM book WHERE title LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + word + "%");
            ResultSet cursor = statement.executeQuery();
            while (cursor.next()) {
                collect.add(createBook(cursor));
            }
        }
        return collect;
    }
    @Override
    public Optional<Book> getById(int id) throws SQLException {
        String sql = "SELECT * FROM book WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet cursor = statement.executeQuery();
            if (cursor.next()) {
                return Optional.of(createBook(cursor));
            }
        }
        return Optional.empty();
    }
    @Override
    public void deleteDB() throws SQLException {
        String inquiry = "DROP TABLE book";
        Statement statement = connection.createStatement();
        statement.executeUpdate(inquiry);
    }

    private Book createBook(ResultSet cursor) throws SQLException {
        return new Book(
                cursor.getInt("ID"),
                cursor.getString("title"),
                cursor.getInt("author_ID"));
    }
}

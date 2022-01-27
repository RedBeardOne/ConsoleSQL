package data.dao;

import data.repository.Author;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class AuthorDAO {
    private final Connection connection;

    public AuthorDAO(Connection connection) {
        this.connection = connection;
    }

    public void createTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS author (ID INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(100), birth_year INTEGER)");
        }
    }

    public void insert(Author author) throws SQLException {
        if (author.getID() != 0) {
            throw new IllegalArgumentException("ID is: " + author.getID());
        }
        final String sql = "INSERT INTO author (name, birth_year) VALUES (?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, author.getName());
            statement.setInt(2, author.getBirthYear());

            int affect = statement.executeUpdate();
            if (affect == 0) {
                throw new SQLException("Creating author failed, no row affect");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    author.setID(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating author failed,something went wrong");
                }
            }
        }
    }

    public void update(Author author) throws SQLException {
        if (author.getID() == 0) {
            throw new IllegalArgumentException("ID is not set");
        }
        final String sql = "UPDATE author " +
                "SET name =?, birth_year =?" +
                "WHERE id =?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, author.getName());
            statement.setInt(2, author.getBirthYear());
            statement.setInt(3, author.getID());
            statement.executeUpdate();
        }
    }

    public Collection<Author> getAll() throws SQLException {
        Collection<Author> collect = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet cursor = statement.executeQuery("SELECT * FROM author");
            while (cursor.next()) {
                collect.add(createAuthor(cursor));
            }
        }
        return collect;
    }

    public Optional<Author> getById(int id) throws SQLException {
        String sql = "SELECT * FROM author WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet cursor = statement.executeQuery();
            if (cursor.next()) {
                return Optional.of(createAuthor(cursor));
            }
        }
        return Optional.empty();
    }

    public Collection<Author> getByName(String text) throws SQLException {
        String sql = "SELECT * FROM author WHERE name = '%%%?%%'";
        Collection<Author> collect = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, text);
            {
                ResultSet cursor = statement.executeQuery();
                while (cursor.next()) {
                    collect.add(createAuthor(cursor));
                }
            }
            return collect;
        }
    }

    public void deleteDB() throws SQLException {
        String inquiry = "DROP TABLE author";
        Statement statement = connection.createStatement();
        statement.executeUpdate(inquiry);
    }

    private Author createAuthor(ResultSet cursor) throws SQLException {
        return new Author(
                cursor.getInt("id"),
                cursor.getString("name"),
                cursor.getInt("birth_year"));
    }
}

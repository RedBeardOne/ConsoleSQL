package data.dao;

import data.repository.Book;
import data.repository.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class UserDAO implements ILibraryRepository<User> {
    private final Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS user (ID INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(100))");
        }
    }

    @Override
    public void deleteDB() throws SQLException {
        String inquiry = "DROP TABLE user";
        Statement statement = connection.createStatement();
        statement.executeUpdate(inquiry);
    }

    @Override
    public void insert(User user) throws SQLException {
        if (user.getUserId() != 0) {
            throw new IllegalArgumentException("ID is: " + user.getUserId());
        }
        final String sql = "INSERT INTO user (name) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getName());

            int affect = statement.executeUpdate();
            if (affect == 0) {
                throw new SQLException("Creating user failed, no row affect");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setUserId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating user failed,something went wrong");
                }
            }
        }
    }

    @Override
    public void update(User user) throws SQLException {
        if (user.getUserId() == 0) {
            throw new IllegalArgumentException("ID is not set");
        }
        final String sql = "UPDATE user " +
                "SET name =?" +
                "WHERE id =?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getName());
            statement.setInt(2, user.getUserId());
            statement.executeUpdate();
        }
    }

    @Override
    public Collection<User> getAll() throws SQLException {
        Collection<User> collect = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet cursor = statement.executeQuery("SELECT * FROM user");
            while (cursor.next()) {
                collect.add(createUser(cursor));
            }
        }
        return collect;
    }

    @Override
    public Optional<User> getById(int id) throws SQLException {
        String sql = "SELECT * FROM user WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet cursor = statement.executeQuery();
            if (cursor.next()) {
                return Optional.of(createUser(cursor));
            }
        }
        return Optional.empty();
    }

    @Override
    public Collection<User> getAllItemsByIdOwner(int id) throws SQLException {
        throw new SQLException("Method not  implemented yet, here is nothing to return , it do not have owners");
    }

    @Override
    public Collection<User> getItemsByName(String word) throws SQLException {
        Collection<User> collect = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE name LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + word + "%");
            ResultSet cursor = statement.executeQuery();
            while (cursor.next()) {
                collect.add(createUser(cursor));
            }
        }
        return collect;
    }

    private User createUser(ResultSet cursor) throws SQLException {
        return new User(cursor.getInt("ID"), cursor.getString("name"));

    }
}

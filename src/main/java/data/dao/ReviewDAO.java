package data.dao;

import data.repository.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class ReviewDAO implements ILibraryRepository<Review> {
    private final Connection connection;

    public ReviewDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS review (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "text VARCHAR(100), " +
                            "user_ID INTEGER," +
                            "book_ID INTEGER)");
        }
    }

    @Override
    public void deleteDB() throws SQLException {
        String inquiry = "DROP TABLE review";
        Statement statement = connection.createStatement();
        statement.executeUpdate(inquiry);
    }

    @Override
    public void insert(Review review) throws SQLException {
        if (review.getId() != 0) {
            throw new IllegalArgumentException("ID is: " + review.getId());
        }
        if (review.getBookId() == 0 || review.getUserId() == 0) {
            throw new IllegalArgumentException("First you have to set book and user");
        }
        final String sql = "INSERT INTO review (text, user_ID, book_ID) VALUES (?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, review.getText());
            statement.setInt(2, review.getUserId());
            statement.setInt(3, review.getBookId());

            int affect = statement.executeUpdate();
            if (affect == 0) {
                throw new SQLException("Creating review failed, no row affect");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    review.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating book failed,something went wrong");
                }
            }
        }
    }

    @Override
    public void update(Review review) throws SQLException {
        if (review.getId() == 0) {
            throw new IllegalArgumentException("ID is not set");
        }
        final String sql = "UPDATE review " +
                "SET text =?, user_ID =?, book_ID =?" +
                "WHERE id =?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, review.getText());
            statement.setInt(2, review.getUserId());
            statement.setInt(3, review.getBookId());
            statement.setInt(4, review.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public Collection<Review> getAll() throws SQLException {
        Collection<Review> collect = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet cursor = statement.executeQuery("SELECT * FROM review");
            while (cursor.next()) {
                collect.add(createReview(cursor));
            }
        }
        return collect;
    }

    @Override
    public Collection<Review> getAllItemsByIdOwner(int Id) throws SQLException {
        String sql = "Select review.ID, review.text, review.user_ID, review.book_ID FROM review " +
                "JOIN book ON review.book_ID = book.id " +
                "WHERE review.book_ID = ?";
        Collection<Review> collect = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, Id);
            ResultSet cursor = statement.executeQuery();
            while (cursor.next()) {
                collect.add(createReview(cursor));
            }
        }
        return collect;
    }

    @Override
    public Collection<Review> getItemsByName(String word) throws SQLException {
        String sql = "Select review.ID, review.text, review.user_ID, review.book_ID " +
                "FROM review " +
                "JOIN user ON review.book_ID =user.ID " +
                "WHERE  user.name LIKE ?";
        Collection<Review> collect = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + word + "%");
            ResultSet cursor = statement.executeQuery();
            while (cursor.next()) {
                collect.add(createReview(cursor));
            }
        }
        return collect;
    }

    @Override
    public Optional<Review> getById(int id) throws SQLException {
        String sql = "SELECT * FROM review WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet cursor = statement.executeQuery();
            if (cursor.next()) {
                return Optional.of(createReview(cursor));
            }
        }
        return Optional.empty();
    }


    private Review createReview(ResultSet cursor) throws SQLException {
        return new Review(
                cursor.getInt("ID"),
                cursor.getString("text"),
                cursor.getInt("user_ID"),
                cursor.getInt("book_ID"));
    }
}

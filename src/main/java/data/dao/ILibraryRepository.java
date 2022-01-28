package data.dao;

import data.repository.Book;
import data.repository.Review;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public interface ILibraryRepository<T> {

    void createTable() throws SQLException;

    void insert(T item) throws SQLException;

    void update(T item) throws SQLException;

    Collection<T> getAll() throws SQLException;

    Optional<T> getById(int id) throws SQLException;

    public Collection<T> getAllItemsByIdOwner(int id) throws SQLException;

    public Collection<T> getItemsByName(String word) throws SQLException;

    void deleteDB() throws SQLException;
}

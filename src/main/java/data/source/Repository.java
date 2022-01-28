package data.source;

import data.dao.ILibraryRepository;
import data.repository.*;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

public class Repository implements IRepositiry {
    ILibraryRepository<Author> authorDAO;
    ILibraryRepository<Book> bookDAO;
    ILibraryRepository<User> userDAO;
    ILibraryRepository<Review> reviewDAO;

    public Repository(ILibraryRepository<Author> authorDAO,
                      ILibraryRepository<Book> bookDAO,
                      ILibraryRepository<User> userDAO,
                      ILibraryRepository<Review> reviewDAO) {
        this.authorDAO = authorDAO;
        this.bookDAO = bookDAO;
        this.userDAO = userDAO;
        this.reviewDAO = reviewDAO;
    }


    @Override
    public void showTable(Type type) {
        try {
            switch (type) {
                case Author:
                    readCollection(authorDAO.getAll());
                    break;
                case Book:
                    readCollection(bookDAO.getAll());
                    break;
                case User:
                    readCollection(userDAO.getAll());
                    break;
                case Review:
                    readCollection(reviewDAO.getAll());
                    break;
                default:
                    System.out.println("No such type");
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getById(Type type, int Id) {
        try {
            switch (type) {
                case Author:
                    authorDAO.getById(Id);
                    break;
                case Book:
                    bookDAO.getById(Id);
                    break;
                case User:
                    userDAO.getById(Id);
                    break;
                case Review:
                    reviewDAO.getById(Id);
                    break;
                default:
                    System.out.println("No such type or ID");
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getByText(Type type, String text) {
        try {
            switch (type) {
                case Author:
                    authorDAO.getItemsByName(text);
                    break;
                case Book:
                    bookDAO.getItemsByName(text);
                    break;
                case User:
                    userDAO.getItemsByName(text);
                    break;
                case Review:
                    reviewDAO.getItemsByName(text);
                    break;
                default:
                    System.out.println("No such type or ID");
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void readCollection(Collection<? extends Entity> collection) {
        for (Entity entity : collection) {
            entity.getReadable();
        }
    }

}


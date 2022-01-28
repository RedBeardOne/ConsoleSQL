package data.repository;

public class Book implements Entity{
    private int id;
    private String title;
    private int authorId;

    public Book(String title, int authorId) {
        this.title = title;
        this.authorId = authorId;
    }

    public Book(int id, String title, int authorId) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Book: " + "id = " + id + ", title '" + title + '\'' + ", author_Id = " + authorId;
    }

    @Override
    public String getReadable() {
        return toString();
    }
}

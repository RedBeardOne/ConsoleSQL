package data.repository;

public class Review {

    private int Id;
    private String text;
    private int userId;
    private int bookId;

    public Review(String text, int userId, int bookId) {
        this.text = text;
        this.userId = userId;
        this.bookId = bookId;
    }

    public Review(int id, String text, int userId, int bookId) {
        Id = id;
        this.text = text;
        this.userId = userId;
        this.bookId = bookId;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getId() {
        return Id;
    }

    public String getText() {
        return text;
    }

    public int getUserId() {
        return userId;
    }

    public int getBookId() {
        return bookId;
    }



    @Override
    public String toString() {
        return "Review: \n" + text + '\n' +
                " userId = " + userId +
                ", bookId = " + bookId;
    }
}

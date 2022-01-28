package data.repository;

public class User implements Entity{
    private int userId;
    private String name;

    public User(int userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public User() {
    }



    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "User " + "name '" + name + '\'' + ", userId = " + userId;
    }

    @Override
   public String getReadable() {
        return toString();
    }
}

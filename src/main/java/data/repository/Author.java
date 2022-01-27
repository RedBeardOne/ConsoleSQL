package data.repository;

public class Author {
    private int ID;
    private String name;
    private int birthYear;

    public Author(int ID, String name, int birthYear) {
        this.ID = ID;
        this.name = name;
        this.birthYear = birthYear;
    }

    public Author(String name, int birthYear) {
        this.name = name;
        this.birthYear = birthYear;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "Author " +
                "ID = " + ID + ", name \"" + name + "\", birth_year - " + birthYear;
    }
}

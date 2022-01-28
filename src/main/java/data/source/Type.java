package data.source;

import java.util.NoSuchElementException;

public enum Type {

    User("User"),
    Author("Author"),
    Book("Book"),
    Review("Reviev");

    private final String type;

    Type(String type) {
        this.type = type;
    }

    public static Type getType(String s) {
        for (Type t : Type.values()) {
            if (t.type.equals(s)) {
                return t;
            }
        }
        throw new NoSuchElementException("No such type " + s);
    }
}

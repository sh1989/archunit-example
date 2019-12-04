package uk.co.samhogy.archunit.example.domain;

public class User {
    private final int id;
    private final String username;

    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }
}

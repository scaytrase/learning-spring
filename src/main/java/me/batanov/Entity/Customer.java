package me.batanov.Entity;

public class Customer {
    private long id;
    private String username, email;

    public Customer(long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, username='%s', email='%s']",
                id, username, email);
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
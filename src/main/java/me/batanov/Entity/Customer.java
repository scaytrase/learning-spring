package me.batanov.Entity;

import javax.persistence.*;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @ManyToOne
    private Organization organization;

    protected Customer() {
    }

    public Customer(String username, String email, Organization organization) {
        this.username = username;
        this.email = email;
        this.organization = organization;
        organization.addCustomer(this);
    }

    public Organization getOrganization() {
        return organization;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, username='%s', email='%s', organization='%s']",
                id, username, email, organization.toString());
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

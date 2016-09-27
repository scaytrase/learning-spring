package me.batanov.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import me.batanov.Views;

import javax.persistence.*;

@Entity
@Table(name = "customer")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
    @JsonManagedReference

    private Organization organization;

    protected Customer() {
    }

    public Customer(String username, String email, Organization organization) {
        this.username = username;
        this.email = email;
        this.organization = organization;
        organization.addCustomer(this);
    }

    @JsonView(Views.Public.class)
    public Organization getOrganization() {
        return organization;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, username='%s', email='%s', organization='%s']",
                id, username, email, organization.toString());
    }

    @JsonView(Views.Public.class)
    public long getId() {
        return id;
    }

    @JsonView(Views.Public.class)
    public String getUsername() {
        return username;
    }

    @JsonView(Views.Internal.class)
    public String getEmail() {
        return email;
    }
}

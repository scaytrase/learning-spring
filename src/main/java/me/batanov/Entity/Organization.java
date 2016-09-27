package me.batanov.Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "organization")
public class Organization {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "description")
    private String description;

    // JPA cannot into unattended lazy loading, so EAGER prevents causing "could not initialize - no session" exception
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "organization")
    private Collection<Customer> customers = new ArrayList<>();

    @SuppressWarnings("unused")
    protected Organization() {
    }

    public Organization(String description) {
        this.description = description;
    }

    public Collection<Customer> getCustomers() {
        return customers;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return String.format("Customer[id=%d, description='%s', total='%d']", id, description, customers.size());
    }

    public void addCustomer(Customer customer) {
        if (!customers.contains(customer)) {
            customers.add(customer);
        }
    }
}

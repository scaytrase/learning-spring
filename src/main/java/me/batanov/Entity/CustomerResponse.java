package me.batanov.Entity;

import com.fasterxml.jackson.annotation.JsonView;
import me.batanov.Views;

public class CustomerResponse {

    private final long id;
    private final Customer customer;

    public CustomerResponse(long id, Customer customer) {
        this.id = id;
        this.customer = customer;
    }

    @JsonView(Views.Public.class)
    public long getId() {
        return id;
    }

    @JsonView(Views.Public.class)
    public Organization getOrgranization() {
        return customer.getOrganization();
    }

    @JsonView(Views.Public.class)
    public Customer getCustomer() {
        return customer;
    }
}

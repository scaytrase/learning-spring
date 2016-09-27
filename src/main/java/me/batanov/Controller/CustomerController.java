package me.batanov.Controller;

import com.fasterxml.jackson.annotation.JsonView;
import me.batanov.Entity.Customer;
import me.batanov.Entity.CustomerResponse;
import me.batanov.Repository.CustomerRepository;
import me.batanov.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class CustomerController {
    private final AtomicLong counter = new AtomicLong();

    private final CustomerRepository repository;

    @Autowired
    public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @RequestMapping("/greeting/{id}/")
    @JsonView(Views.Public.class)
    public CustomerResponse greeting(@PathVariable("id") Long id) {

        Customer customer = repository.findOne(id);

        return new CustomerResponse(counter.incrementAndGet(), customer);
    }
}

package me.batanov.Controller;

import me.batanov.Entity.Customer;
import me.batanov.Entity.Greeting;
import me.batanov.Repository.CustomerRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class CustomerController {

    private static final String template = "Hello, %s at %s (%d customers)!";
    private final AtomicLong counter = new AtomicLong();

    private final CustomerRepository repository;

    @Autowired
    public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @RequestMapping("/greeting/{id}/")
    public Greeting greeting(@PathVariable("id") Long id) {

        Customer customer = repository.findOne(id);

        return new Greeting(
                counter.incrementAndGet(),
                String.format(
                        template,
                        customer.getUsername(),
                        customer.getOrganization().getDescription(),
                        customer.getOrganization().getCustomers().size()
                )
        );
    }
}

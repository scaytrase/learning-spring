package me.batanov.Controller;

import me.batanov.Entity.Customer;
import me.batanov.Entity.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/greeting/{id}/")
    public Greeting greeting(@PathVariable("id") Integer id) {

        List<Customer> customers = jdbcTemplate.query(
                "SELECT id, username, email FROM customers WHERE id = ?", new Object[]{id},
                (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("username"), rs.getString("email")));

        return new Greeting(counter.incrementAndGet(),
                String.format(template, customers.get(customers.size() - 1).getUsername()));
    }
}

package me.batanov;

import me.batanov.Entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);


    @Autowired
    JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {

        log.info("Creating tables");

        jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE customers(" +
                "id SERIAL, username VARCHAR(255), email VARCHAR(255))");

        // Split up the array of whole names into an array of first/last names
        List<Object[]> splitUpNames = Arrays.asList("scaytrase pavel@batanov.me", "pavel scaytrase@gmail.com").stream()
                .map(name -> name.split(" "))
                .collect(Collectors.toList());

        // Use a Java 8 stream to print out each tuple of the list
        splitUpNames.forEach(name -> log.info(String.format("Inserting customer record for %s %s", name[0], name[1])));

        // Uses JdbcTemplate's batchUpdate operation to bulk load data
        jdbcTemplate.batchUpdate("INSERT INTO customers(username, email) VALUES (?,?)", splitUpNames);

        log.info("Querying for customer records where first_name = 'Josh':");
        jdbcTemplate.query(
                "SELECT id, username, email FROM customers WHERE username = ?", new Object[]{"scaytrase"},
                (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("username"), rs.getString("email"))
        ).forEach(customer -> log.info(customer.toString()));
    }
}

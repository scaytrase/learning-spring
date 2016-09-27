package me.batanov;

import me.batanov.Entity.Customer;
import me.batanov.Entity.Organization;
import me.batanov.Repository.CustomerRepository;
import me.batanov.Repository.OrganizationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class DemoApplication {

    private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(CustomerRepository customerRepository, OrganizationRepository organizationRepository) {
        return (args) -> {
            Organization office = new Organization("My office");
            organizationRepository.save(office);

            organizationRepository.save(new Organization("Other office"));

            // save a couple of customers
            customerRepository.save(new Customer("scaytrase", "pavel@batanov.me", office));
            customerRepository.save(new Customer("pavel", "scaytrase@gmail.com", office));

            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (Customer customer : customerRepository.findAll()) {
                log.info(customer.toString());
            }
            log.info("");

            // fetch an individual customer by ID
            Customer customer = customerRepository.findOne(1L);
            log.info("Customer found with findOne(1L):");
            log.info("--------------------------------");
            log.info(customer.toString());
            log.info("");

            // fetch customers by last name
            log.info("Customer found with findByLastName('scaytrase'):");
            log.info("--------------------------------------------");
            for (Customer customerFind : customerRepository.findByUsername("scaytrase")) {
                log.info(customerFind.toString());
            }
            log.info("");
        };
    }

}

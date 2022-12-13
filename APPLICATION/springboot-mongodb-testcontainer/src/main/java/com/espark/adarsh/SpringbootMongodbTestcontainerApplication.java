package com.espark.adarsh;

import com.espark.adarsh.entities.Employee;
import com.espark.adarsh.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootMongodbTestcontainerApplication implements CommandLineRunner {

    @Autowired
    private EmployeeRepository repository;

    @Override
    public void run(String... args) throws Exception {
        this.repository.deleteAll();

        // save a couple of customers
        this.repository.save(new Employee("1", "Adarsh", "Kumar"));
        this.repository.save(new Employee("2", "Radha", "Singh"));

        // fetch all customers
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (Employee employee : this.repository.findAll()) {
            System.out.println(employee);
        }
        System.out.println();

        // fetch an individual customer
        System.out.println("Employee found with findByFirstName('Adarsh'):");
        System.out.println("--------------------------------");
        System.out.println(this.repository.findByFirstName("Adarsh"));

        System.out.println("Customers found with findByLastName('Singh'):");
        System.out.println("--------------------------------");
        for (Employee employee : this.repository.findByLastName("Singh")) {
            System.out.println(employee);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMongodbTestcontainerApplication.class, args);
    }

}

package com.adamzareba.spring.security.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/* 
 *JPA repositories are not picked up by component scans since they are just interfaces whos 
 *concrete classes are created dynamically as beans by Spring Data provided you have included the 
 *@EnableJpaRepositories annotation in your configuration:
 * */

@SpringBootApplication(exclude = JmxAutoConfiguration.class)
@EnableJpaRepositories("com.adamzareba.spring.security.oauth2.repository")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

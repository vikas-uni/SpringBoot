package com.howtodoinjava.example.employee;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.howtodoinjava.example.employee.beans.Employee;

@Service
public class GitHubLookupService {
 
    private static final Logger logger = LoggerFactory.getLogger(GitHubLookupService.class);
 
    @Async
    public CompletableFuture<Employee> findUser(String user) throws InterruptedException {
        logger.info("Looking up " + user);
        logger.info("Start processing request");
        Employee results = new Employee(RandomUtils.nextInt(), user);
        // Artificial delay of 10s for demonstration purposes
        Thread.sleep(10000L);
        logger.info("Completed processing request");
        return CompletableFuture.completedFuture(results);
    }
    
    @Async
    public CompletableFuture<Employee> findUser2(String user) throws InterruptedException {
        logger.info("Looking up findUser2" + user);
        logger.info("Start processing request findUser2");
        Employee results = new Employee(RandomUtils.nextInt(), user);
        // Artificial delay of 10s for demonstration purposes
        Thread.sleep(15000L);
        logger.info("Completed processing request findUser2");
        return CompletableFuture.completedFuture(results);
    }
 
}

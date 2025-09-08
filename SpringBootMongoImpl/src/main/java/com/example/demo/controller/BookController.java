package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Book;
import com.example.demo.repo.BookRepo;

import java.util.List;
import java.util.Optional;
  
@RestController
public class BookController {
	private static final Logger logger = LoggerFactory.getLogger(BookController.class);
	
    @Autowired
    private BookRepo repo;
  
    @PostMapping("/addBook")
    public String saveBook(@RequestBody Book book){
        repo.save(book);
        logger.info("adding book: {}", book);
        return "Added Successfully";
    }
  
    @GetMapping("/findAllBooks")
    public List<Book> getBooks() {
        logger.info("finding all books...");
        return repo.findAll();
    }
  
    @GetMapping("/getBook/{id}")
    public Optional<Book> getBook(@PathVariable int id) {
    	logger.info("finding book by id: {}", id);
        return repo.findById(id);
    }
  
    
    @DeleteMapping("/delete/{id}")
    public String deleteBook(@PathVariable int id){
        repo.deleteById(id);
        logger.info("deleting book by id: {}", id);
        return "Deleted Successfully";
    }
  
}

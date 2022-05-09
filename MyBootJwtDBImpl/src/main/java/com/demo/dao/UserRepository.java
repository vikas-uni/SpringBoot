package com.demo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demo.model.DAOUser;

/*
Next we define the UserDao which is an interface that extends the Spring Framework class CrudRepository. 
CrudRepository class is a generics and takes the following two parameters as arguments
- What type of Object will this repository be working with- In our case DAOUser and 
Id will be what type of object- Integer(since id defined in the UserDao class is Integer) 
Thats the only configuration required for the repository class. 
The required operation of inserting user details in DB will now be handled. 
*/
@Repository
public interface UserRepository extends CrudRepository<DAOUser, Integer> {
	DAOUser findByUsername(String username);
}
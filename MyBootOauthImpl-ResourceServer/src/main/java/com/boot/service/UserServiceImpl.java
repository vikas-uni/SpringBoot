package com.boot.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.boot.dao.UserDao;
import com.boot.dao.model.User;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;


	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		User user = userDao.findByUsername(userId);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user.getRole()));
	}

	private List<SimpleGrantedAuthority> getAuthority(String role) {
		return Arrays.asList(new SimpleGrantedAuthority(role));
	}

	public List<User> findAll() {
		List<User> list = new ArrayList<User>();
		userDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	public User save(User user) {
		user.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userDao.save(user);
    }
}
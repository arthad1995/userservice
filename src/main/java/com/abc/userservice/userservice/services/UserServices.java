package com.abc.userservice.userservice.services;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.abc.userservice.userservice.Daos.UserDao;
import com.abc.userservice.userservice.beans.User;
import com.abc.userservice.userservice.http.Response;

@Service
public class UserServices {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserDao ud;

	@Transactional(propagation = Propagation.REQUIRED)
	public Response register(User user) throws Throwable {
		// checked
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		ud.save(user);
		return new Response(true);
	}

	public Response getUserById(int id) {
		
		Optional<User> u = ud.findById(id);
		System.out.println(u);
		if (!u.isPresent()) {
			return new Response(false,"user not found");
		}
		
		return new Response(true,u.get());
	}
	public Response getUserByName(String name) {
		User u = ud.findByUsername(name);
		if(u==null) {
			return new Response(false,"user not found");
		}
		
		return new Response(true,u);
	}
	
	public Response getAllUser() {
		// checked
		List<User> list = ud.findAll(); 
		return new Response(true,list);
	}
	
	
	public Response updateUser(User user) {
		User u = ud.findByUsername(user.getUsername());
		System.out.println(u + " from data base");
		System.out.println(user + " from para");
		if(u==null) {
			return new Response(false,"user not found");
		}
		if(user.getEmail()!=null) {
			u.setEmail(user.getEmail());
		}
		if(user.getName()!=null) {
			u.setName(user.getName());
		}
		if(user.getPassword()!=null) {
			u.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		ud.save(u);
		return new Response(true);
	}
	
	public Response delete(int id) {
		try {
			Optional<User> user = ud.findById(id);
	
			if (!user.isPresent()) {
				return new Response(false,"user not found");
			}
			ud.delete(user.get());
		
		return new Response(true);
	}
		catch(Exception e) {
			return new Response(false, e.getMessage());
		}
	
	
}
}

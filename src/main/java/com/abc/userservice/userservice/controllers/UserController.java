package com.abc.userservice.userservice.controllers;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abc.userservice.userservice.beans.User;
import com.abc.userservice.userservice.http.Response;
import com.abc.userservice.userservice.services.UserServices;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserServices userService;
	
	@PostMapping
	public Response register(@RequestBody User user) {
		// checked
		System.out.println(user);
		Response r =new Response(false);
		try {
			
			r=userService.register(user);
			
		} catch (Throwable e) {
			
			return new Response(false,e.getMessage());
		}
		return r;
	}
	
	@GetMapping
	public Response getAll() {
		// checked
		return userService.getAllUser();
	}
	
	@GetMapping("/id/{id}")
	public Response getUserById(@PathVariable int id) {
		return userService.getUserById(id);
	}
	
	@GetMapping("/name/{name}")
	public Response getUserByName(@PathVariable String name) {

		return userService.getUserByName(name);
	}
	@PutMapping
	public Response edit(@RequestBody User user) {
		return userService.updateUser(user);
	}
	@DeleteMapping("/{id}")
	public Response delete(@PathVariable int id) {
		return userService.delete(id);
	}

}

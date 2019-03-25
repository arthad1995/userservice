package com.abc.userservice.userservice.controllers;


import java.util.List;

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
	
	@PostMapping("/register")
	public Response<String> register(@RequestBody User user) {
		// checked
		System.out.println(user);
		Response<String> r =new Response<String>(false);
		try {
			
			r=userService.register(user);
			
		} catch (Throwable e) {
			
			return new Response<String>(false,e.getMessage());
		}
		return r;
	}
	
	@GetMapping
	public Response<List<User>> getAll() {
		// checked
		return userService.getAllUser();
	}
	
	@PostMapping("/resetRequest/{name}")
	public Response<String> resetEmail(@PathVariable String name) {
		return userService.sendResetEmail(name);
	}
	
	@PutMapping("/reset")
	public Response<String> resetPwd(@RequestBody User user) {
		return userService.resetPwd(user);
	}
	
	@GetMapping("/id/{id}")
	public Response<User> getUserById(@PathVariable int id) {
		return userService.getUserById(id);
	}
	
	@GetMapping("/name/{name}")
	public Response<User> getUserByName(@PathVariable String name) {

		return userService.getUserByName(name);
	}
	@PutMapping
	public Response<String> edit(@RequestBody User user) {
		return userService.updateUser(user);
	}
	@DeleteMapping("/{id}")
	public Response<String> delete(@PathVariable int id) {
		return userService.delete(id);
	}

}

package com.abc.userservice.userservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.abc.userservice.userservice.Daos.UserDao;
import com.abc.userservice.userservice.beans.User;




@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDao ud;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	//	Spring will call this function to load the user from DB
		User user = ud.findByUsername(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("User " + username + " was not found in the database");
		}
		return user;

	}
}
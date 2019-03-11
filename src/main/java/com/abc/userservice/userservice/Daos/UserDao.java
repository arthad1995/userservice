package com.abc.userservice.userservice.Daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abc.userservice.userservice.beans.User;

public interface UserDao extends JpaRepository<User, Integer>{
		public User findByUsername(String name);
}

package com.abc.userservice.userservice.Daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abc.userservice.userservice.beans.Profile;

public interface ProfileDao extends JpaRepository<Profile, Integer>{

}

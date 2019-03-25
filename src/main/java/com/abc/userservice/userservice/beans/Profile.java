package com.abc.userservice.userservice.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "ABC_PROFILE")
public class Profile implements GrantedAuthority{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5548881839638705203L;
	@Id
	private int id;
	@Column
	private String role;
	public Profile(int id, String role) {
		super();
		this.id = id;
		this.role = role;
	}
	public Profile() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return role;
	}
	@Override
	public String toString() {
		return "Profile [id=" + id + ", role=" + role + "]";
	}
	
	
}
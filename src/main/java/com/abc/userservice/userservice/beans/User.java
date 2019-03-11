package com.abc.userservice.userservice.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;

@Entity
@Table(name = "ABC_USER")
public class User implements UserDetails{
	///changed msi to abc
	@Id
	@SequenceGenerator(name="abc_user_seq_gen", sequenceName="ABC_USER_SEQUENCE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="abc_user_seq_gen")
	private int id;
	

	@Column
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	@Column
	private String username;
	@Column
	private String name;
	@Column
	private String email;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinTable(name = "ABC_USER_PROFILE", //join table name
			//define how join table will join with join with current table
			joinColumns = {
					@JoinColumn(name = "USER_ID", referencedColumnName = "ID")
					
			},
			//define how join table will join with the other table
			inverseJoinColumns = {
					@JoinColumn(name = "PROFILE_ID", referencedColumnName = "ID")		
			}
			)
	private Profile profile;
	
	
	
	

	public User(int id, String password, String username, String name, String email, Profile profile) {
		super();
		this.id = id;
		this.password = password;
		this.username = username;
		this.name = name;
		this.email = email;
		this.profile = profile;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password + ", username=" + username + ", name=" + name + ", email="
				+ email + ", profile=" + profile + "]";
	}
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		List<Profile> l = new ArrayList<Profile>();
		l.add(profile);
		return l;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	

}

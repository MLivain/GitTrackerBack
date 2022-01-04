package com.ynov.gittracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user")
public class UserDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    

    @NotNull
    @NotBlank
    @Column(unique=true)
    private String username;

    @NotNull
    @NotBlank
    @Column
    @JsonIgnore
    private String password;

    @Column
    private String role;
    
    @NotNull
    @NotBlank
    @Column(name = "email", unique = true)
    private String email;
   

	@Column(name = "githubToken")
    private String githubToken;


    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Role> roles = new ArrayList<Role>();
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGithubToken() {
		return githubToken;
	}

	public void setGithub_token(String githubToken) {
		this.githubToken = githubToken;
	}


	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	
    
}


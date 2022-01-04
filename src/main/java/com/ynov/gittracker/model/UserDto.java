package com.ynov.gittracker.model;

import java.util.List;

public class UserDto {
    private String username;
    private String password;
    private String role;
    private String github_token;
    private String email;
    private List<Role> roles;

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
    
    public String getGithubToken() {
		return github_token;
	}
    
    public String getEmail() {
		return email;
	}
    
    public List<Role> getRoles() {
		return roles;
	}
    

}

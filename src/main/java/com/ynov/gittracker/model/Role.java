package com.ynov.gittracker.model;

import org.hibernate.annotations.Type;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "role")
public class Role {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

	
	@NotNull
	@ManyToOne 
	@JoinColumn(name="userId", nullable=false)
	private UserDao user; 
	
	@NotNull
	@ManyToOne 
	@JoinColumn(name="projectId", nullable=false)
	private Project project; 
	
	@NotNull
	@NotBlank
	@Column(name="role")
	private String role;

	public UserDao getUser() {
		return user;
	}

	public void setUser(UserDao user) {
		this.user = user;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
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
	
	
	
}
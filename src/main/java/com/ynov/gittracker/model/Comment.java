package com.ynov.gittracker.model; 

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Entity
@Table(name = "comment")
public class Comment {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

	@NotNull
	@ManyToOne 
	@JoinColumn(name="author_id", nullable=false)
	private UserDao author; 

	@NotNull
	@ManyToOne
	@JoinColumn(name="issue_id", nullable=false)
	private Issue issue; 

	@NotNull
	@NotBlank
	@Column(name="content")
	private String content; 


    @Column(name="created_at")
    private Date createdAt;

    @Column(name="updated_at")
    private Date updateAt;


	public Comment () {}

	public Comment (UserDao author, Issue issue, String message) {
		this.setAuthor(author);
		this.setIssue(issue);
		this.setContent(message);
	}

	public long getId() {
		return id; 
	}

	public void setId(long id) {
		this.id = id; 
	}

	public UserDao getAuthor() {
		return author; 
	}

	public void setAuthor(UserDao author) {
		this.author = author; 
	}


	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue2) {
		this.issue = issue2;
	}

	public String getContent() {
		return content; 
	}

	public void setContent(String message) {
		this.content = message; 
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}
	
	
}
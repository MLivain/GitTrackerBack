package com.ynov.gittracker.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @NotNull
    @NotBlank
    @Column(name="name")
    private String name;
    
    @NotNull
	@ManyToOne
    @JoinColumn(name="pmId")
    private UserDao projectManager;
    
    @NotNull
    @NotBlank
    @Column(name="gitUrl")
    private String gitUrl;
    

	@NotNull
    @NotBlank
    @Column(name="content")
    private String content;
    
    @NotNull
    @NotBlank
    @Column(name="status")
    private String status;
    
    @NotNull
    @NotBlank
    @Column(name="shortDescription")
    private String shortDescription;

   
    @Column(name="created_at")
    private Date createdAt;

    
    @Column(name="updated_at")
    private Date updateAt;

//    @OneToMany
//    @JoinColumn(referencedColumnName="id")
//    private List<Issue> issues;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getGit_url() {
		return gitUrl;
	}

	public void setGit_url(String git_url) {
		this.gitUrl = git_url;
	}

	public String getShort_description() {
		return shortDescription;
	}

	public void setShort_description(String short_description) {
		this.shortDescription = short_description;
	}

//	public List<Issue> getIssues() {
//		return issues;
//	}
//
//	public void setIssues(List<Issue> issues) {
//		this.issues = issues;
//	}

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

    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public UserDao getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(UserDao projectManager) {
		this.projectManager = projectManager;
	}

	public String getGitUrl() {
		return gitUrl;
	}

	public void setGitUrl(String gitUrl) {
		this.gitUrl = gitUrl;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
   
	
}

package com.ynov.gittracker.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "event")
public class Event {

	public String EVENT_USER = "USER";
    public String EVENT_PROJECT = "PROJECT";
    public String EVENT_COMMENT = "COMMENT";
    public String EVENT_ISSUE = "ISSUE";
    public String EVENT_ROLE = "ROLE";

    public String EVENT_ACTION_CREATE = "create";
    public String EVENT_ACTION_CREATE_ISSUE = "create issue";
    public String EVENT_ACTION_UPDATE = "update";
    public String EVENT_ACTION_DELETE = "delete";
    public String EVENT_ACTION_ACCEPT = "accepted";
    public String EVENT_ACTION_REFUSE = "refused";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @NotBlank
    @Column(name="title")
    private String title;

    
    @Column(name="created_at")
    private Date createdAt;

    
    @Column(name="updated_at")
    private Date updateAt;

    @NotNull
    @ManyToOne
    @JoinColumn(name="user_id")
    private UserDao user;

    @NotNull
    @NotBlank
    @JoinColumn(name="entity_id")
    private String entityId;

    @NotNull
    @NotBlank
    @JoinColumn(name="entity")
    private String entity;

    // ------------------------ >

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public UserDao getAuthor() {
        return user;
    }

    public void setAuthor(UserDao author) {
        this.user = author;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entity2) {
        this.entityId = entity2;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }
}

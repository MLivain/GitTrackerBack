package com.ynov.gittracker.repository;

import java.util.UUID; 

import com.ynov.gittracker.model.Comment;
import com.ynov.gittracker.model.Issue;
import com.ynov.gittracker.model.UserDao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface CommentRepository extends JpaRepository<Comment, Long> {

	Comment findByIssue(Issue issue);

	Comment findByAuthor(UserDao loggedUser);

}
package com.ynov.gittracker.repository;

import java.util.UUID; 

import com.ynov.gittracker.model.Comment; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface CommentRepository extends JpaRepository<Comment, UUID> {

}
package com.ynov.gittracker.service; 

import com.ynov.gittracker.model.Comment;
import com.ynov.gittracker.model.Event;
import com.ynov.gittracker.model.*;
import com.ynov.gittracker.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommentService {
	@Autowired
	private CommentRepository commentRepository; 

	@Autowired
    private EventService eventService;

	// --------------------- // 

	public Comment create(Comment comment) {
		Event event = new Event();
		this.eventService.create(
			event.EVENT_PROJECT, 
			comment.getAuthor(),
			event.EVENT_ACTION_CREATE,
			comment
		);

		return commentRepository.save(comment);
	}

	public Comment update(Comment comment) throws Exception {
		Event event = new Event();
		this.eventService.create(
			event.EVENT_PROJECT, 
			comment.getAuthor(),
			event.EVENT_ACTION_UPDATE,
			comment
		);
		
		return commentRepository.save(comment); 
	}

	public List<Comment> getAllComments() {
		return commentRepository.findAll();
	}

	public Comment getCommentByCommentId(long id) {
		return commentRepository.findById(id).orElse(null);
	}

}
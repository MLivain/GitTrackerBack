package com.ynov.gittracker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ynov.gittracker.model.Comment;
import com.ynov.gittracker.model.Issue;
import com.ynov.gittracker.model.UserDao;
import com.ynov.gittracker.service.CommentService;
import com.ynov.gittracker.service.SecurityService;
import com.ynov.gittracker.service.UserService;

import javax.validation.Valid;

import java.util.*;

@RestController
public class CommentController {
	 @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    // --------------------- //

    @RequestMapping(path="/add-test-comment", method = RequestMethod.GET)
    public void addTestComment(Issue issue) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDao loggedUser = userService.getUserByUsername(authentication.getName());
        
        List<UserDao> likes = new ArrayList<UserDao>();

        Comment comment = new Comment();
        comment.setAuthor(loggedUser);
        comment.setIssue(issue);
        comment.setContent("Hello world");
        commentService.create(comment);
    }

    @Operation(summary = "Récupération des commentaires pour une issue")
    @RequestMapping(path="/", method= RequestMethod.GET)
    public List<Comment> getCommentsForProject(@RequestParam(value="issue") Issue issue) {
    	if (issue != null) {
    		return issue.getComments();
    	}
    	return null;
    }

    // TODO : 
    // @Operation(summary = "Récupération des commentaires écrit par un user")
    // @Operation(summary = "Récupération des commentaires likés par un user") 
    // @Operation(summary = "Création d'un commentaire par un user")
    // @Operation(summary = "Ajout d'un like sur un commentaire par un user")
    // @Operation(summary = "Suppression d'un commentaire par l'auteur") 

    // @Operation(summary = "Modification d'un commentaire par l'auteur")
    @RequestMapping(path="/", method= RequestMethod.PUT)
    public Object updateComment(@Valid @RequestBody Comment comment) throws Exception {
        Authentication loggedUser = securityService.getLoggedUser();
        UserDao user = userService.getUserByUsername(loggedUser.getName());

        if (user != comment.getAuthor()){
            throw new Exception("Vous n'êtes pas l'auteur de ce commentaire");
        }

        return commentService.update(comment);
    }
}
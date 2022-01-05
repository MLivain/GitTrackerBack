package com.ynov.gittracker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ynov.gittracker.config.JwtTokenUtil;
import com.ynov.gittracker.model.Comment;
import com.ynov.gittracker.model.Issue;
import com.ynov.gittracker.model.Project;
import com.ynov.gittracker.model.UserDao;
import com.ynov.gittracker.service.CommentService;
import com.ynov.gittracker.service.IssueService;
import com.ynov.gittracker.service.SecurityService;
import com.ynov.gittracker.service.UserService;

import javax.validation.Valid;

import java.util.*;

@RestController
@CrossOrigin("*")
public class CommentController {
	 @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private IssueService issueService;

    JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
    // --------------------- //

    @RequestMapping(path="/add-test-comment", method = RequestMethod.GET)
    public void addTestComment(@RequestHeader (name="Authorization") String token) {
    	token = token.replace("Bearer ", "");
    	System.out.println(token);
		String username = jwtTokenUtil.getUsernameFromToken(token);
        UserDao loggedUser = userService.getUserByUsername(username);
        Issue issue = issueService.getAllIssues().get(0);
        Comment comment = new Comment();
        comment.setAuthor(loggedUser);
        comment.setIssue(issue);
        comment.setContent("Hello world");
        commentService.create(comment);
    }

//    @Operation(summary = "Récupération des commentaires pour une issue")
//    @RequestMapping(path="/", method= RequestMethod.GET)
//    public List<Comment> getCommentsForProject(@RequestParam(value="issue") Issue issue) {
//    	if (issue != null) {
//    		return issue.getComments();
//    	}
//    	return null;
//    }

    
    // @Operation(summary = "Modification d'un commentaire par l'auteur")
    @RequestMapping(path="/comment", method= RequestMethod.PUT)
    public Comment updateComment(@Valid @RequestBody Comment comment) throws Exception {
        Authentication loggedUser = securityService.getLoggedUser();
        UserDao user = userService.getUserByUsername(loggedUser.getName());

        if (user != comment.getAuthor()){
            throw new Exception("Vous n'êtes pas l'auteur de ce commentaire");
        }

        return commentService.update(comment);
    }
    
    @Operation(summary = "Récupération d'un commentaire")
    @RequestMapping(path = "/comment", method = RequestMethod.GET)
    public Comment getComment(@RequestParam(value = "id") long id) {
        return commentService.getCommentByCommentId(id);
    }
    
    @RequestMapping(path="/comment", method= RequestMethod.POST)
    public Comment createComment(@Valid @RequestHeader (name="Authorization") String token,
    		@RequestParam(value = "issue_id") long issue_id,
    		@RequestParam(value = "content") String content) {
    	token = token.replace("Bearer ", "");
    	System.out.println(token);
		String username = jwtTokenUtil.getUsernameFromToken(token);
        UserDao loggedUser = userService.getUserByUsername(username);
        Issue issue = issueService.getIssueByIssueId(issue_id);
        Comment comment = new Comment();
        comment.setAuthor(loggedUser);
        comment.setIssue(issue);
        comment.setContent(content);
        return commentService.create(comment);
    }
    
    @RequestMapping(path="/user/comments", method= RequestMethod.GET)
    public Comment getCommentsByUser(@Valid @RequestHeader (name="Authorization") String token) {
    	token = token.replace("Bearer ", "");
    	System.out.println(token);
		String username = jwtTokenUtil.getUsernameFromToken(token);
        UserDao loggedUser = userService.getUserByUsername(username);
        return commentService.getCommentsByUser(loggedUser);
    }
    
    @RequestMapping(path="/issue/comments", method= RequestMethod.GET)
    public Comment getCommentsByIssue(@Valid @RequestParam(value = "issue_id") long issue_id) {
    	Issue issue = issueService.getIssueByIssueId(issue_id);
        return commentService.getCommentsByIssue(issue);
    }
}
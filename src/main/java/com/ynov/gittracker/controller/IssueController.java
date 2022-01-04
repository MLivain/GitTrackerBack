package com.ynov.gittracker.controller;

import com.ynov.gittracker.model.Issue;
import com.ynov.gittracker.model.UserDao;
import com.ynov.gittracker.service.IssueService;
import com.ynov.gittracker.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
public class IssueController {

    @Autowired
    private IssueService issueService;
    
    @Autowired
    private UserService userService;

    // --------------------- >

    @RequestMapping(path = "/add-test-issue", method = RequestMethod.GET)
    public void addTestIssue() {
        Issue research = new Issue();
        UserDao author = userService.getUserByUsername("neerfix");
        research.setTitle("Bar");
        research.setContent("Aller boire un verre a la kolok");
        research.setAuthor(author);
        research.setCriticity("Anomalie");
        issueService.createOrUpdate(research);
    }

    @Operation(summary = "Récupération d'une issue")
    @RequestMapping(path = "/issue", method = RequestMethod.GET)
    public Issue getIssue(@RequestParam(value = "id") UUID id) {
        return issueService.getIssueByIssueId(id);
    }

    @Valid
    @Operation(summary = "Création ou mise à jour d'une issue")
    @RequestMapping(path = "/issue", method = RequestMethod.PUT)
    public Issue addOrUpdateIssue(@RequestBody Issue issue) {
        return issueService.createOrUpdate(issue);
    }

    @Operation(summary = "Auteur de l'issue")
    @RequestMapping(path = "/research/author", method = RequestMethod.GET)
    public UserDao getAuthorByIssue(@RequestParam(value = "id") UUID id) {
        return issueService.getIssueByIssueId(id).getAuthor();
    }

    @Operation(summary = "Récupération de toutes les issues")
    @RequestMapping(path = "/issues", method = RequestMethod.GET)
    public List<Issue> getIssues() {
        return (List<Issue>) issueService.getAllIssues();
    }

    @Operation(summary = "Suppression d'une issue à partir de son id")
    @RequestMapping(path = "/issue", method = RequestMethod.DELETE)
    public void deleteIssue(@RequestParam(value = "id") UUID id) {
    	issueService.delete(id);
    }
}

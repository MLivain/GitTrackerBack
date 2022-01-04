package com.ynov.gittracker.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ynov.gittracker.model.Comment;
import com.ynov.gittracker.model.Event;
import com.ynov.gittracker.model.Issue;
import com.ynov.gittracker.model.Project;
import com.ynov.gittracker.model.UserDao;
import com.ynov.gittracker.repository.IssueRepository;
import org.springframework.web.server.ResponseStatusException;

@Service
public class IssueService {

	@Autowired
	private IssueRepository issueRepository;
	
	@Autowired
	private EventService eventService;

    public Issue createOrUpdate(Issue issue) {
        Event event = new Event();
        this.eventService.create(event.EVENT_ISSUE, issue.getAuthor(), event.EVENT_ACTION_CREATE, issue);
        return issueRepository.save(issue);
    }

    public Issue getIssueByIssueId(long id) {
        return issueRepository.findById(id).orElse(null);
    }
    
    public String getIssueCriticityByIssueId(long id) {
        return issueRepository.findById(id).orElse(null).getCriticity();
    }
    public UserDao getResearchAuthorByResearchId(long id) {
        return issueRepository.findById(id).orElse(null).getAuthor();
    }

    public List<Issue> getAllIssues() { return issueRepository.findAll(); }

    public void delete(long id) {
        Issue deleteIssue = this.getIssueByIssueId(id);

        if (deleteIssue == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Exit not found"
            );
        }

        Event event = new Event();
        this.eventService.create(event.EVENT_ISSUE, deleteIssue.getAuthor(), event.EVENT_ACTION_DELETE, deleteIssue);

        issueRepository.deleteById(deleteIssue.getId());
    }

	public List<Issue> getIssuesByProject(Project project) {
		return issueRepository.findByProject(project);
	}
    
    
}

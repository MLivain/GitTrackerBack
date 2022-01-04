package com.ynov.gittracker.service;

import com.ynov.gittracker.model.Event;
import com.ynov.gittracker.model.Issue;
import com.ynov.gittracker.model.Project;
import com.ynov.gittracker.model.UserDao;
import com.ynov.gittracker.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectService
{
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EventService eventService;

    // --------------------- >

    public Project create(Project project) {
        Event event = new Event();
        this.eventService.create(event.EVENT_PROJECT,project.getProjectManager(), event.EVENT_ACTION_CREATE, project);

        return projectRepository.save(project);
    }

    public Project update(Project project) {
        Event event = new Event();
        this.eventService.create(event.EVENT_PROJECT,project.getProjectManager(), event.EVENT_ACTION_UPDATE, project);

        return projectRepository.save(project);
    }

    public Project getProjectByProjectId(UUID id) {
        return projectRepository.findById(id).orElse(null);
    }


    public List<Project> getAllProjects() { return projectRepository.findAll(); }

    public void delete(UUID id) {
    	Project deleteExit = this.getProjectByProjectId(id);

        if (deleteExit == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Exit not found"
            );
        }

        projectRepository.delete(deleteExit);
    }

   



	public Project addUserIssueToProject(Project project, Issue issue) {
		if (project != null) {
            List<Issue> listIssue = project.getIssues();

            if (issue != null) {
            	listIssue.add(issue);
                project.setIssues(listIssue);
            }

            projectRepository.save(project);
        }

        return project;
	}

	public Project deleteIssueForProject(Project project, UserDao user, Issue issue) {
		if (project != null) {
			List<Issue> listIssue = project.getIssues();

            if (issue != null) {
            	listIssue.remove(issue);
                project.setIssues(listIssue);
            }

            projectRepository.save(project);
        }

        Event event = new Event();
        this.eventService.create(event.EVENT_USER, user, event.EVENT_ACTION_CREATE_ISSUE, project.getId());

        return project;
	}
}

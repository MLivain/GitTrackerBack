package com.ynov.gittracker.controller;

import com.ynov.gittracker.config.JwtTokenUtil;
import com.ynov.gittracker.model.Issue;
import com.ynov.gittracker.model.Project;
import com.ynov.gittracker.model.Role;
import com.ynov.gittracker.model.UserDao;
import com.ynov.gittracker.service.IssueService;
import com.ynov.gittracker.service.ProjectService;
import com.ynov.gittracker.service.RoleService;
import com.ynov.gittracker.service.SecurityService;
import com.ynov.gittracker.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private IssueService issueService;

    @Autowired
    private SecurityService securityService;
    
    JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

    // --------------------- >

    @RequestMapping(path = "/add-test-project", method = RequestMethod.GET)
    public void addTestProject(@RequestHeader (name="Authorization") String token) {
    	token = token.replace("Bearer ", "");
    	System.out.println(token);
		String username = jwtTokenUtil.getUsernameFromToken(token);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDao loggedUser = userService.getUserByUsername(username);
        Project project = new Project();
        project.setName("First project");
        project.setProjectManager(loggedUser);
        project.setShortDescription("setShortDescription");
        project.setContent("setContent");
        project.setStatus("setStatus");
        project.setGitUrl("UnUrlGIT");
        project.setCreatedAt(new Date());
        project.setUpdateAt(new Date());

        Role role = new Role();
        role.setProject(project);
        role.setUser(loggedUser);
        role.setRole("ADMIN");
        roleService.createOrUpdate(role);
        
        projectService.create(project);
    }

    @Operation(summary = "Récupération d'un projet")
    @RequestMapping(path = "/project", method = RequestMethod.GET)
    public Project getExit(@RequestParam(value = "id") UUID id) {
        return projectService.getProjectByProjectId(id);
    }

    @Operation(summary = "Création d'un projet")
    @RequestMapping(path = "/project", method = RequestMethod.POST)
    @ResponseBody
    public Project addProject(@Valid @RequestParam(value = "name") String name,
    		@RequestParam(value = "shortDescription") String shortDescription,
    		@RequestParam(value = "content") String content,
    		@RequestParam(value = "status") String status,
    		@RequestParam(value = "githubUrl") String githubUrl,
    		@RequestHeader (name="Authorization") String token) {
    	token = token.replace("Bearer ", "");
    	System.out.println(token);
		String username = jwtTokenUtil.getUsernameFromToken(token);
        UserDao loggedUser = userService.getUserByUsername(username);
        Project project = new Project();
        project.setName(name);
        project.setProjectManager(loggedUser);
        project.setShortDescription(shortDescription);
        project.setContent(content);
        project.setStatus(status);
        project.setGitUrl(githubUrl);
        project.setCreatedAt(new Date());
        project.setUpdateAt(new Date());
        project = projectService.create(project);
        Role role = new Role();
        role.setProject(project);
        role.setUser(loggedUser);
        role.setRole("ADMIN");
        roleService.createOrUpdate(role);
        
        return project;
    }

    @Operation(summary = "Mise à jour d'un projet")
    @RequestMapping(path = "/project", method = RequestMethod.PUT)
    public Object updateProject(@Valid 
    		@RequestParam(value = "id") UUID id,
    		@RequestParam(value = "name") String name,
    		@RequestParam(value = "shortDescription") String shortDescription,
    		@RequestParam(value = "content") String content,
    		@RequestParam(value = "status") String status,
    		@RequestParam(value = "githubUrl") String githubUrl,
    		@RequestHeader (name="Authorization") String token) throws Exception {
    	Project project = this.projectService.getProjectByProjectId(id);token = token.replace("Bearer ", "");
    	System.out.println(token);
		String username = jwtTokenUtil.getUsernameFromToken(token);
        UserDao loggedUser = userService.getUserByUsername(username);
        Role role = roleService.getRoleByProjectAndUsername(project,loggedUser.getUsername());
        if (role.getRole() != "ADMIN"){
            throw new Exception("Vous n'êtes pas admin de ce projet");
        }else {
        	if(name!="") {
            	project.setName(name);
        	}
        	if(shortDescription!="") {
                project.setShortDescription(shortDescription);
        	}
        	if(content!="") {
                project.setContent(content);
        	}
        	if(status!="") {
                project.setStatus(status);        		
        	}
        	if(githubUrl!="") {
                project.setGitUrl(githubUrl);
        	}
            project.setUpdateAt(new Date());
        }
        return projectService.update(project);
    }
    
    

    @Operation(summary = "Ajout d'une issue à un projet")
    @RequestMapping(path = "/project/issues", method = RequestMethod.PUT)
    public Project proposeIssueForProject(@Valid @RequestParam(value = "id") UUID id, @RequestParam(value = "user_id") String username, @RequestParam(value = "issue_id") UUID issue_id) throws Exception {
    	UserDao user = this.userService.getUserByUsername(username);
        Project project = this.projectService.getProjectByProjectId(id);
        Issue issue = this.issueService.getIssueByIssueId(issue_id);
        
        if (user == null) {
            throw new Exception("user not found");
        }

        if (project == null) {
            throw new Exception("project not found");
        }
        
        if (issue == null) {
            throw new Exception("issue not found");
        }
        
        return projectService.addUserIssueToProject(project, issue);
    }

    @Operation(summary = "Récupération de touts les projets")
    @RequestMapping(path = "/projects", method = RequestMethod.GET)
    public List<Project> getProjects() {
        return (List<Project>) projectService.getAllProjects();
    }

    @Operation(summary = "Suppression d'un projet à partir de son id")
    @RequestMapping(path = "/project", method = RequestMethod.DELETE)
    public void deleteProject(@RequestParam(value = "id") UUID id) {
    	projectService.delete(id);
    }
    
    @Operation(summary = "Ajout du role utilisateur à un projet")
    @RequestMapping(path = "/project/roles", method = RequestMethod.PUT)
    public Object addRoleToProject(@Valid @RequestParam(value = "id") UUID id, @RequestParam(value = "username") String username, @RequestParam(value = "role_string") String role_string) throws Exception {
        Authentication loggedUser = this.securityService.getLoggedUser();
        UserDao user = userService.getUserByUsername(loggedUser.getName());
        UserDao userToAdd = userService.getUserByUsername(username);
        Project project = projectService.getProjectByProjectId(id);
        Role roletoAdd = new Role();
        roletoAdd.setProject(project);
        roletoAdd.setUser(userToAdd);
        return roleService.createOrUpdate(roletoAdd);
    }
}

package com.ynov.gittracker.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ynov.gittracker.model.Issue;
import com.ynov.gittracker.model.UserDao;
import com.ynov.gittracker.service.SecurityService;
import com.ynov.gittracker.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    // --------------------- >
    @CrossOrigin
    @RequestMapping(path = "/add-test-user", method = RequestMethod.GET)
    public UserDao addTestUser() {
    	UserDao user = new UserDao();
        user.setEmail("neerfix@neerfix.com");
        user.setUsername("neerfix");
        user.setPassword("qwertyuiop");
        user.setRole("ADMIN");
        userService.createOrUpdate(user);
        return user;
    }
    @CrossOrigin
    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public UserDao getUser(@RequestParam(value = "id") String username) {
        return userService.getUserByUsername(username);
    }
    @CrossOrigin
    @Operation(summary = "Création ou mise à jour d'un utilisateur")
    @RequestMapping(path = "/user", method = RequestMethod.PUT)
    public UserDao addOrUpdateUser(@Valid @RequestBody UserDao user) {
        return userService.createOrUpdate(user);
    }
    @CrossOrigin
    @Operation(summary = "Récupération de tous les utilisateurs")
    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<UserDao> getUsers() {
        return (List<UserDao>) userService.getAllUsers();
    }
    @CrossOrigin
    @Operation(summary = "Suppression d'un utilisateur à partir de son username")
    @RequestMapping(path = "/user", method = RequestMethod.DELETE)
    public void deleteUser(@RequestParam(value = "username") String username) {
        userService.delete(username);
    }

//    @Operation(summary = "Issues de l'auteur ")
//    @RequestMapping(path = "/user/issues", method = RequestMethod.GET)
//    public List<Issue> getissuesByAuthor(@RequestParam(value = "id") String id) {
//        return userService.getIssuesByUserId(id);
//    }

    
    
//    @Valid
//    @Operation(summary = "Ajouter une issue à l'utilisateur")
//    @RequestMapping(path = "/user/issues", method = RequestMethod.PUT)
//    public UserDao addUserForExit(@RequestParam(value = "id") String id, @RequestParam(value = "research") long research) {
//        return userService.addIssueForUser(id, research);
//    }
    @CrossOrigin
    @Operation(summary = "Mise à jour du mot de passe d'un utilisateur")
    @RequestMapping(path = "/user/updatePassword", method = RequestMethod.GET)
    public void setPassword(@RequestParam(value = "username") String username,
                            @RequestParam(value = "old") String oldPassword,
                            @RequestParam(value = "new") String newPassword) throws IllegalAccessException {
        userService.setPassword(username, oldPassword, newPassword);
    }
}

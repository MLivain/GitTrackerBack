package com.ynov.gittracker.service;

import com.ynov.gittracker.model.Event;
import com.ynov.gittracker.model.Issue;
import com.ynov.gittracker.model.UserDao;
import com.ynov.gittracker.repository.IssueRepository;
import com.ynov.gittracker.repository.UserRepository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

@Service
public class UserService implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EventService eventService;

    // --------------------- >

    public UserDao createOrUpdate(UserDao user) {
        if (StringUtils.isNotEmpty(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    public UserDao getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<UserDao> getAllUsers() { return (List<UserDao>) userRepository.findAll(); }

    public void delete(String username) {
    	UserDao user = this.getUserByUsername(username);

        if (user == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found"
            );
        }
        
        userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws
            UsernameNotFoundException {
    	UserDao user = this.getUserByUsername(username);
        if (user != null) {
            List<GrantedAuthority> authorities = new
                    ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority("ROLE_" +
            		user.getRole()));

            return new
                    org.springframework.security.core.userdetails.User(user.getUsername(),
                    user.getPassword(),
                    authorities);
        }
        throw new UsernameNotFoundException("User '" + username + "' not found or inactive");
    }

    public void setPassword(String username, String oldPassword, String newPassword) throws IllegalAccessException {
    	UserDao user = this.getUserByUsername(username);
        if (user != null) {
            String encodedOldPassword = passwordEncoder.encode(oldPassword);
            String encodedNewPassword = passwordEncoder.encode(newPassword);
            if (StringUtils.isEmpty(user.getPassword()) ||
                    StringUtils.equals(user.getPassword(), encodedOldPassword)) {
                user.setPassword(encodedNewPassword);
                userRepository.save(user);
            } else {
                throw new IllegalAccessException("Invalid old password");
            }
        }
    }




}

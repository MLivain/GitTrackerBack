package com.ynov.gittracker.service;

import com.ynov.gittracker.model.Event;
import com.ynov.gittracker.model.Project;
import com.ynov.gittracker.model.UserDao;
import com.ynov.gittracker.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class EventService
{
    @Autowired
    private EventRepository eventRepository;

    public void create(String type, UserDao author, String action, Object entity) {

        System.out.println(author.getUsername());
        Event event = new Event();
        event.setCreatedAt(new Date());
        event.setUpdateAt(new Date());
        event.setTitle("New " + type +  action + " by : " + author.getUsername() + " at " + event.getCreatedAt() + " on this entity : " + entity);
        event.setAuthor(author);
        event.setEntity(entity.getClass().getName());
        event.setEntityId(entity.toString());

        this.eventRepository.save(event);
    }

    public void updateUser(String type, UserDao author, String action, Object entity, String entityToAdd) {

        Event event = new Event();
        event.setCreatedAt(new Date());
        event.setUpdateAt(new Date());
        event.setTitle("New " + type + "("+entityToAdd+")" +  action + " by : " + author + " at " + event.getCreatedAt() + " on this entity : " + entity);
        event.setAuthor(author);
        event.setEntity(entity.getClass().getName());
        event.setEntityId(entity.toString());

        this.eventRepository.save(event);
    }
    
}

package com.ynov.gittracker.repository;

import com.ynov.gittracker.model.Issue;
import com.ynov.gittracker.model.Project;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

	List<Issue> findByProject(Project project);

}

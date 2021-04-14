package com.nearforce.academy.projectboard.service;

import com.nearforce.academy.projectboard.domain.ProjectTask;
import com.nearforce.academy.projectboard.repository.ProjectTaskRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectTaskService {

    private final ProjectTaskRepository projectTaskRepository;

    ProjectTaskService(ProjectTaskRepository projectTaskRepository) {
        this.projectTaskRepository = projectTaskRepository;
    }

    public ProjectTask saveOrUpdateProjectTask(ProjectTask projectTask) {
        if (projectTask.getStatus() == null || projectTask.getStatus().equals("")) {
            projectTask.setStatus("TO_DO");
        }
        return projectTaskRepository.save(projectTask);
    }

    public Iterable<ProjectTask> findAll() {
        return projectTaskRepository.findAll();
    }

    public Optional<ProjectTask> findById(Long id) {
        return projectTaskRepository.findById(id);
    }

    public void delete(Long id) {
        projectTaskRepository.deleteById(id);
    }
}

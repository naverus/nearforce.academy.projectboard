package com.nearforce.academy.projectboard.service;

import com.nearforce.academy.projectboard.domain.ProjectTask;
import com.nearforce.academy.projectboard.repository.ProjectTaskRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    private ProjectTaskRepository projectTaskRepository;

    ProjectTaskService(ProjectTaskRepository projectTaskRepository) {
        this.projectTaskRepository = projectTaskRepository;
    }

    public ProjectTask saveOrUpdateProjectTask(ProjectTask projectTask) {
        if (projectTask.getStatus() == null || projectTask.getStatus().equals("")) {
            projectTask.setStatus("TO_DO");
        }
            return projectTaskRepository.save(projectTask);
    }
}

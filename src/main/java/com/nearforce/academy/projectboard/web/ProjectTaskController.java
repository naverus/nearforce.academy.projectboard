package com.nearforce.academy.projectboard.web;

import com.nearforce.academy.projectboard.domain.ProjectTask;
import com.nearforce.academy.projectboard.service.ProjectTaskService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/board")
@CrossOrigin
public class ProjectTaskController {
    private ProjectTaskService projectTaskService;

    ProjectTaskController(ProjectTaskService projectTaskService) {
        this.projectTaskService = projectTaskService;
    }

    @PostMapping("")
    public ResponseEntity<?> saveProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        ProjectTask resultProjectTask = projectTaskService.saveOrUpdateProjectTask(projectTask);
        return new ResponseEntity<>(resultProjectTask, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public Iterable<ProjectTask> getAllProjectTasks() {
        return projectTaskService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProjectTaskById(@PathVariable Long id) {
        Optional<ProjectTask> result = projectTaskService.findById(id);
        if (result.isEmpty()) {
            return new ResponseEntity<>("Project not found with id " + id + ". ", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result.get(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProjectTaskById(@PathVariable Long id) {
        try {
            projectTaskService.delete(id);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Project not found with id " + id + ". ", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Project with id " + id + " was deleted. ", HttpStatus.OK);
    }
}

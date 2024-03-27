package com.task_manager.controllers;

import com.task_manager.domain.NewTask;
import com.task_manager.domain.Task;
import com.task_manager.services.TaskServiceImpl;
import com.task_manager.services.interfaces.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody NewTask newTask) {

        Task task = taskService.createTask(newTask);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(task.id()).toUri();

        return ResponseEntity.created(location).body(task);
    }
}

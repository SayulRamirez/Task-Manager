package com.task_manager.controllers;

import com.task_manager.domain.NewTask;
import com.task_manager.domain.Task;
import com.task_manager.domain.UpdateTask;
import com.task_manager.services.TaskServiceImpl;
import com.task_manager.services.interfaces.TaskService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Task> createTask(@Valid @RequestBody NewTask newTask) {

        Task task = taskService.createTask(newTask);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(task.id()).toUri();

        return ResponseEntity.created(location).body(task);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Task> updateTask(@Valid @RequestBody UpdateTask updateTask) {

        Task task = taskService.updateTask(updateTask);

        return ResponseEntity.ok(task);
    }

}

package com.task_manager.controllers;

import com.task_manager.domain.*;
import com.task_manager.services.TaskServiceImpl;
import com.task_manager.services.interfaces.TaskService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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
    public ResponseEntity<TaskCreateResponse> createTask(@Valid @RequestBody RegisterTask registerTask) {

        TaskCreateResponse task = taskService.createTask(registerTask);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(task.id()).toUri();

        return ResponseEntity.created(location).body(task);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<TaskResponse> updateTask(@Valid @RequestBody UpdateTask updateTask) {

        TaskResponse task = taskService.updateTask(updateTask);

        return ResponseEntity.ok(task);
    }

    @PutMapping("/status")
    @Transactional
    public ResponseEntity<TaskResponse> updateStatus(@Valid @RequestBody UpdateStatus updateStatus) {

        TaskResponse task = taskService.updateStatus(updateStatus);

        return ResponseEntity.ok(task);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<List<TaskResponse>> findAllTask(@PathVariable Long id) {

        List<TaskResponse> tasks = taskService.findAll(id);

        return ResponseEntity.ok(tasks);
    }

    @GetMapping
    public ResponseEntity<TaskResponse> findOneTask(@Valid @RequestBody SimpleTask simpleTask) {

        TaskResponse task = taskService.findByIdAuthor(simpleTask);

        return ResponseEntity.ok(task);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Void> deleteTask(@Valid @RequestBody SimpleTask simpleTask) {

        taskService.deleteTask(simpleTask);

        return ResponseEntity.noContent().build();
    }

}

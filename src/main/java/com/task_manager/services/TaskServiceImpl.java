package com.task_manager.services;

import com.task_manager.domain.Author;
import com.task_manager.domain.NewTask;
import com.task_manager.domain.Task;
import com.task_manager.entities.AuthorEntity;
import com.task_manager.entities.TaskEntity;
import com.task_manager.exceptions.AuthorNotFound;
import com.task_manager.repositories.AuthorRepository;
import com.task_manager.repositories.TaskRepository;
import com.task_manager.services.interfaces.TaskService;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    private final AuthorRepository authorRepository;
    private final TaskRepository taskRepository;

    public TaskServiceImpl(AuthorRepository authorRepository, TaskRepository taskRepository) {
        this.authorRepository = authorRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public Task createTask(NewTask newTask) {

        AuthorEntity authorEntity = authorRepository.findById(newTask.id_author()).orElse(null);

        if (authorEntity == null) throw new AuthorNotFound("Author not found whit id " + newTask.id_author());

        TaskEntity taskEntity = new TaskEntity(
                null,
                newTask.title(),
                newTask.description(),
                null,
                newTask.start_date(),
                authorEntity
        );

        taskRepository.save(taskEntity);

        return new Task(
                taskEntity.getId(),
                taskEntity.getTitle(),
                taskEntity.getDescription(),
                taskEntity.getStatus(),
                taskEntity.getStartDate(),
                new Author(taskEntity.getAuthor().getId(), taskEntity.getAuthor().getNick())
        );
    }
}

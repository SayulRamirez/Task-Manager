package com.task_manager.services;

import com.task_manager.domain.*;
import com.task_manager.entities.AuthorEntity;
import com.task_manager.entities.TaskEntity;
import com.task_manager.exceptions.AuthorNotFound;
import com.task_manager.exceptions.TaskNotFound;
import com.task_manager.repositories.AuthorRepository;
import com.task_manager.repositories.TaskRepository;
import com.task_manager.services.interfaces.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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

    @Override
    public Task updateTask(UpdateTask updateTask) {

        TaskEntity taskEntity = taskRepository.findTaskByIdAndIdAuthor(updateTask.id_task(), updateTask.id_author());

        if (taskEntity == null) throw new TaskNotFound("No matches found for task id " + updateTask.id_task() + " and author id " + updateTask.id_author());


        if (!taskEntity.getStartDate().equals(updateTask.start_date()) && LocalDateTime.now().isBefore(updateTask.start_date())) taskEntity.setStartDate(updateTask.start_date());


        taskEntity.setTitle(updateTask.title());
        taskEntity.setDescription(updateTask.description());
        taskEntity.setStatus(updateTask.status());

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

    @Override
    public List<Task> findAll(Long id) {
        List<TaskEntity> taskEntities = taskRepository.findAllById(id);

        if (taskEntities.isEmpty()) throw new TaskNotFound("No assignments were found with the author: " + id);

        List<Task> tasks = new ArrayList<>();

        taskEntities.forEach(taskEntity -> tasks.add(new Task(
                taskEntity.getId(),
                taskEntity.getTitle(),
                taskEntity.getDescription(),
                taskEntity.getStatus(),
                taskEntity.getStartDate(),
                new Author(taskEntity.getAuthor().getId(), taskEntity.getAuthor().getNick()))));

        return tasks;
    }

    @Override
    public Task findByIdAuthor(OnlyTask onlyTask) {

        TaskEntity taskEntity = taskRepository.findTaskByIdAndIdAuthor(onlyTask.id_task(), onlyTask.id_author());

        if (taskEntity == null) throw new TaskNotFound("No assignments were found with the author: " + onlyTask.id_author() + " and task: " + onlyTask.id_task());

        return new Task(
                taskEntity.getId(),
                taskEntity.getTitle(),
                taskEntity.getDescription(),
                taskEntity.getStatus(),
                taskEntity.getStartDate(),
                new Author(taskEntity.getAuthor().getId(), taskEntity.getAuthor().getNick())
        );
    }

    @Override
    public void deleteTask(OnlyTask onlyTask) {

        TaskEntity taskEntity = taskRepository.findTaskByIdAndIdAuthor(onlyTask.id_task(), onlyTask.id_author());

        if (taskEntity == null) throw new TaskNotFound("No assignments were found with the author: " + onlyTask.id_author() + " and task: " + onlyTask.id_task());

        taskRepository.delete(taskEntity);
    }
}

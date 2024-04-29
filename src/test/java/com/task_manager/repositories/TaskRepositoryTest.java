package com.task_manager.repositories;

import com.task_manager.entities.AuthorEntity;
import com.task_manager.entities.TaskEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AuthorRepository authorRepository;
    private TaskEntity taskEntity;

    @BeforeEach
    void setup() {

        AuthorEntity authorEntity = new AuthorEntity(null, "juan");

        authorRepository.save(authorEntity);

        taskEntity = new TaskEntity(null,
                "Tarea de prueba",
                "Sobre esta tarea se realizarán todos los casos de prueba",
                null,
                LocalDateTime.of(2024, Month.DECEMBER, 20, 9, 12),
                authorEntity);
    }

    @Test
    void createTask() {

        TaskEntity task = taskRepository.save(taskEntity);

        assertThat(task).isNotNull();
        assertThat(task.getId()).isNotZero();
    }

    @Test
    void notFoundTaskByIdAndAuthorId() {

        TaskEntity task = taskRepository.findTaskByIdAndIdAuthor(1L, 2L);

        assertThat(task).isNull();
    }

    @Test
    void findTaskByIdAndAuthorId() {
        TaskEntity taskSave = taskRepository.save(taskEntity);

        TaskEntity taskFind = taskRepository.findTaskByIdAndIdAuthor(taskSave.getId(), taskSave.getAuthor().getId());

        assertThat(taskFind).isNotNull();
        assertThat(taskFind.getTitle()).isEqualTo("Tarea de prueba");
    }

    @Test
    void updateTask() {

        TaskEntity save = taskRepository.save(taskEntity);

        save.setTitle("Cambiando el titulo de la tarea");

        TaskEntity update = taskRepository.save(save);

        assertThat(save.getId()).isEqualTo(update.getId());
        assertThat(update.getTitle()).isEqualTo("Cambiando el titulo de la tarea");
    }

    @Test
    void findAllTaskByIdAuthorIsEmpty() {

        List<TaskEntity> tasks = taskRepository.findAllById(4L);

        assertThat(tasks.isEmpty()).isTrue();
    }

    @Test
    void findAllTaskByIdAuthor() {

        AuthorEntity author = taskEntity.getAuthor();

        TaskEntity secondTask = new TaskEntity(null,
                "Tarea de prueba",
                "Sobre esta tarea se realizarán todos los casos de prueba",
                null,
                LocalDateTime.of(2024, Month.DECEMBER, 20, 9, 12),
                author);

        taskRepository.save(taskEntity);
        taskRepository.save(secondTask);

        List<TaskEntity> tasks = taskRepository.findAllById(author.getId());

        assertThat(tasks.isEmpty()).isFalse();
        assertThat(tasks.size()).isEqualTo(2);
    }

}

package com.task_manager.services;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.task_manager.domain.*;
import com.task_manager.entities.AuthorEntity;
import com.task_manager.entities.TaskEntity;
import com.task_manager.enums.Status;
import com.task_manager.exceptions.AuthorNotFound;
import com.task_manager.exceptions.TaskNotFound;
import com.task_manager.repositories.AuthorRepository;
import com.task_manager.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private AuthorRepository authorRepository;
    @InjectMocks
    private TaskServiceImpl service;
    private TaskEntity entity;

    @BeforeEach
    void setup() {

        entity = new TaskEntity(
                1L,
                "Prueba para la capa del servicio",
                "Esta entidad se usara para probar los tests del servicio de la tarea",
                Status.PENDIENTE,
                LocalDateTime.now().plusDays(1),
                new AuthorEntity(2L, "juan")
        );
    }

    @Test
    void whenSaveTaskTheAuthorNotFound() {

        RegisterTask register = new RegisterTask(
                "Registro de prueba",
                "prueba para cuando no se encuentra el author",
                LocalDateTime.now().plusDays(1L), 3L);

        given(authorRepository.findById(3L)).willReturn(Optional.empty());

        assertThrows(AuthorNotFound.class, () -> service.createTask(register));

        verify(taskRepository, never()).save(any(TaskEntity.class));
    }

    @Test
    void whenSaveTaskIsSuccess() {

        RegisterTask register = new RegisterTask(
                "Prueba para la capa del servicio",
                "Esta entidad se usara para probar los tests del servicio de la tarea",
                LocalDateTime.now().plusDays(1L), 1L);

        AuthorEntity authorEntity = new AuthorEntity(1L, "Prueba exitosa");

        given(authorRepository.findById(1L)).willReturn(Optional.of(authorEntity));

        TaskCreateResponse task = service.createTask(register);

        assertThat(task).isNotNull();
        assertThat(task.title()).isEqualTo(register.title());
        verify(taskRepository, times(1)).save(any(TaskEntity.class));
    }

    @Test
    void whenUpdateTaskNotFound() {

        UpdateTask updateTask = new UpdateTask(1L, "Nuevo titulo", "Creando el test cuando se falla la actualización",
                Status.EN_PROGRESO, LocalDateTime.now().plusDays(1L), 2L);

        given(taskRepository.findTaskByIdAndIdAuthor(updateTask.id_task(), updateTask.id_author())).willReturn(null);

        assertThrows(TaskNotFound.class, () -> service.updateTask(updateTask));

        verify(taskRepository, never()).save(any(TaskEntity.class));
    }

    @Test
    void whenUpdateTaskDateIsEqualsToDateOrigin() {

        UpdateTask updateTask = new UpdateTask(1L, "Nuevo titulo", "Creando el test cuando se falla la actualización",
                Status.EN_PROGRESO, LocalDateTime.now().plusDays(1L), 2L);

        given(taskRepository.findTaskByIdAndIdAuthor(updateTask.id_task(), updateTask.id_author())).willReturn(entity);

        TaskResponse response = service.updateTask(updateTask);

        assertThat(response).isNotNull();
        assertThat(response.start_date()).isEqualTo(entity.getStartDate());
        verify(taskRepository, times(1)).save(any(TaskEntity.class));
    }

    @Test
    void whenUpdateTaskDateIsBeforeToDateOrigin() {

        UpdateTask updateTask = new UpdateTask(1L, "Nuevo titulo", "Creando el test cuando se falla la actualización",
                Status.EN_PROGRESO, LocalDateTime.now().minusDays(1L), 2L);

        given(taskRepository.findTaskByIdAndIdAuthor(updateTask.id_task(), updateTask.id_author())).willReturn(entity);

        TaskResponse response = service.updateTask(updateTask);

        assertThat(response).isNotNull();
        assertThat(response.start_date()).isEqualTo(entity.getStartDate());
        verify(taskRepository, times(1)).save(any(TaskEntity.class));
    }

    @Test
    void whenUpdateTaskDateIsAfterToDateOrigin() {

        LocalDateTime startDate = entity.getStartDate();

        UpdateTask updateTask = new UpdateTask(1L, "Nuevo titulo", "Creando el test cuando se falla la actualización",
                Status.EN_PROGRESO, LocalDateTime.now().plusDays(3L), 2L);

        given(taskRepository.findTaskByIdAndIdAuthor(updateTask.id_task(), updateTask.id_author())).willReturn(entity);

        TaskResponse response = service.updateTask(updateTask);

        assertThat(response).isNotNull();
        assertThat(response.start_date()).isAfter(startDate);
        verify(taskRepository, times(1)).save(any(TaskEntity.class));
    }

    @Test
    void whenUpdateStatusNotFoundTask() {

        UpdateStatus updateStatus = new UpdateStatus(1L, 2L, Status.EN_PROGRESO);

        given(taskRepository.findTaskByIdAndIdAuthor(1L, 2L)).willReturn(null);

        assertThrows(TaskNotFound.class, () -> service.updateStatus(updateStatus));

        verify(taskRepository, never()).save(any(TaskEntity.class));
    }

    @Test
    void whenUpdateStatusIsSuccess() {

        UpdateStatus updateStatus = new UpdateStatus(1L, 2L, Status.EN_PROGRESO);

        Status status = entity.getStatus();

        given(taskRepository.findTaskByIdAndIdAuthor(updateStatus.id_task(), updateStatus.id_author())).willReturn(entity);

        TaskResponse response = service.updateStatus(updateStatus);

        assertThat(response).isNotNull();
        assertThat(response.status()).isNotEqualTo(status);
    }

    @Test
    void whenFindAllTaskByAuthorIsEmpty() {

        given(taskRepository.findAllById(1L)).willReturn(new ArrayList<>());

        assertThrows(TaskNotFound.class, () -> service.findAll(1L));
    }

    @Test
    void whenFindAllTaskByAuthorIsSuccess() {

        List<TaskEntity> entities = Arrays.asList(
                entity,
                new TaskEntity(1L, "Segunda entidad",
                "Esta entidad se usara para probar los tests del servicio de la tarea",
                Status.PENDIENTE, LocalDateTime.now().plusDays(1),
                new AuthorEntity(2L, "juan")));

        given(taskRepository.findAllById(2L)).willReturn(entities);

        List<TaskResponse> response = service.findAll(2L);

        assertThat(response.isEmpty()).isFalse();
        assertThat(response.size()).isEqualTo(2L);
    }

    @Test
    void whenFindSpecificTaskByIdAndAuthorIdNotFound() {

        SimpleTask simpleTask = new SimpleTask(1L, 2L);

        given(taskRepository.findTaskByIdAndIdAuthor(simpleTask.id_task(), simpleTask.id_author())).willReturn(null);

        assertThrows(TaskNotFound.class, () -> service.findByIdAuthor(simpleTask));
    }

    @Test
    void whenFindSpecificTaskByIdAndAuthorIdIsSuccess() {

        SimpleTask simpleTask = new SimpleTask(1L, 2L);

        given(taskRepository.findTaskByIdAndIdAuthor(simpleTask.id_task(), simpleTask.id_author())).willReturn(entity);

        TaskResponse task = service.findByIdAuthor(simpleTask);

        assertThat(task).isNotNull();
        assertThat(task.id()).isEqualTo(entity.getId());

    }

    @Test
    void whenNotFoundTaskToDelete() {

        SimpleTask simpleTask = new SimpleTask(1L, 2L);

        given(taskRepository.findTaskByIdAndIdAuthor(simpleTask.id_task(), simpleTask.id_author())).willReturn(null);

        assertThrows(TaskNotFound.class, () -> service.findByIdAuthor(simpleTask));

    }

    @Test
    void whenDeleteTaskIsSuccess() {

        SimpleTask simpleTask = new SimpleTask(1L, 2L);

        given(taskRepository.findTaskByIdAndIdAuthor(simpleTask.id_task(), simpleTask.id_author())).willReturn(entity);

        service.deleteTask(simpleTask);

        verify(taskRepository, times(1)).delete(any(TaskEntity.class));

    }
}

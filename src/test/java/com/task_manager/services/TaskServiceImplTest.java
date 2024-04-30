package com.task_manager.services;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.task_manager.domain.RegisterTask;
import com.task_manager.domain.TaskCreateResponse;
import com.task_manager.entities.AuthorEntity;
import com.task_manager.entities.TaskEntity;
import com.task_manager.exceptions.AuthorNotFound;
import com.task_manager.repositories.AuthorRepository;
import com.task_manager.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
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
                null,
                "Prueba para la capa del servicio",
                "Esta entidad se usara para probar los tests del servicio de la tarea",
                null,
                LocalDateTime.now().plusDays(1),
                null
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
}

package com.task_manager.services;

import com.task_manager.entities.AuthorEntity;
import com.task_manager.entities.TaskEntity;
import com.task_manager.repositories.AuthorRepository;
import com.task_manager.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

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
                new AuthorEntity(1L, "Prueba")
        );
    }
}

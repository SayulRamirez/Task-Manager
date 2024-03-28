package com.task_manager.domain;

import com.task_manager.enums.Status;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record UpdateTask(

        @NotNull(message = "The field cannot be null")
        Long id_task,

        @NotEmpty(message = "The field cannot be empty")
        @Size(min = 5, max = 50, message = "Must contain between 5 and 50 characters")
        String title,

        @NotEmpty(message = "The field cannot be empty")
        @Size(min = 5, max = 255, message = "Must contain between 5 and 200 characters")
        String description,

        @NotNull(message = "The field cannot be empty")
        Status status,

        @NotNull(message = "The field cannot be null")
        LocalDateTime start_date,

        @NotNull(message = "The field cannot be null")
        Long id_author) {
}

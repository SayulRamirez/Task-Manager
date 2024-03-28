package com.task_manager.domain;

import com.task_manager.enums.Status;
import jakarta.validation.constraints.NotNull;

public record UpdateStatus(

        @NotNull(message = "The field cannot be null")
        Long id_task,

        @NotNull(message = "The field cannot be null")
        Long id_author,

        @NotNull(message = "The field cannot be null")
        Status status
) {
}

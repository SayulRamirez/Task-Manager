package com.task_manager.domain;

import jakarta.validation.constraints.NotNull;

public record OnlyTask(

        @NotNull(message = "The field cannot be null")
        Long id_task,

        @NotNull(message = "The field cannot be null")
        Long id_author
) {
}

package com.task_manager.domain;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(

        @NotEmpty(message = "The field cannot be null")
        String email,

        @NotEmpty(message = "The field cannot be null")
        String password
) {
}

package com.task_manager.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewUser(

        @Email(message = "Check the email, it must be valid")
        @NotNull(message = "The field cannot be empty")
        @Size(min = 5, max = 50, message = "Must contain between 5 and 50 characters")
        String email,

        @NotEmpty(message = "The field cannot be empty")
        @Size(min = 8, max = 10, message = "Must contain between 8 and 10 characters")
        String password,

        @NotEmpty(message = "The field cannot be empty")
        @Size(min = 5, max = 60, message = "Must contain between 5 and 60 characters")
        String nick
) {
}

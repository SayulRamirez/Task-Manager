package com.task_manager.domain;

import com.task_manager.enums.Status;

import java.time.LocalDateTime;

public record TaskResponse(

        Long id,
        String title,
        String description,
        Status status,
        LocalDateTime start_date
) {
}

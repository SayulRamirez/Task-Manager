package com.task_manager.entities;

import com.task_manager.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tasks")
public class TaskEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "start date", nullable = false)
    private LocalDateTime startDate;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_author_task"), nullable = false)
    private Author author;

}

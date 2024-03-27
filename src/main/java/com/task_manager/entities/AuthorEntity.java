package com.task_manager.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "authors")
public class Author implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 60, nullable = false, unique = true)
    private String nick;

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    private List<TaskEntity> tasks = new ArrayList<>();
}

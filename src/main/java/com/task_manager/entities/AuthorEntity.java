package com.task_manager.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "authors")
public class AuthorEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 60, nullable = false, unique = true)
    private String nick;
}

package com.task_manager.controllers;

import com.task_manager.domain.Author;
import com.task_manager.domain.NewUser;
import com.task_manager.services.UserServiceImpl;
import com.task_manager.services.interfaces.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserServiceImpl userService){this.userService = userService;}

    @PostMapping
    @Transactional
    public ResponseEntity<Author> createUser(@Valid @RequestBody NewUser newUser) {

        Author author = userService.createUser(newUser);

        return ResponseEntity.ok(author);
    }
}

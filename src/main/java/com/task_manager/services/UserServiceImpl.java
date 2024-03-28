package com.task_manager.services;

import com.task_manager.domain.Author;
import com.task_manager.domain.NewUser;
import com.task_manager.entities.AuthorEntity;
import com.task_manager.entities.UserEntity;
import com.task_manager.exceptions.DuplicateDate;
import com.task_manager.repositories.AuthorRepository;
import com.task_manager.repositories.UserRepository;
import com.task_manager.services.interfaces.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;

    public UserServiceImpl(AuthorRepository authorRepository, UserRepository userRepository) {
        this.authorRepository = authorRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Author createUser(NewUser newUser) {

        if (userRepository.existsByEmail(newUser.email())) throw new DuplicateDate("The email is already in use: " + newUser.email());
        if (authorRepository.existsByNick(newUser.nick())) throw new DuplicateDate("The nickname is already in use: " + newUser.nick());

        UserEntity userEntity = new UserEntity(null, newUser.email(), newUser.password(), new AuthorEntity(null, newUser.nick()));

        userRepository.save(userEntity);

        return new Author(userEntity.getAuthor().getId(), newUser.nick());
    }
}

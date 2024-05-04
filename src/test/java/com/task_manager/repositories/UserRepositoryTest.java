package com.task_manager.repositories;

import static org.assertj.core.api.Assertions.*;

import com.task_manager.entities.AuthorEntity;
import com.task_manager.entities.UserEntity;
import com.task_manager.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void whenRegisterUser() {

        UserEntity userEntity = new UserEntity(
                null, "email@example.com", "contraseñaMegaSecreta",
                new AuthorEntity(null, "nuevo"), Role.USER
        );

        UserEntity register = userRepository.save(userEntity);

        assertThat(register).isNotNull();
        assertThat(register.getId()).isGreaterThan(0);
        assertThat(register.getAuthor().getId()).isGreaterThan(0);
    }

    @Test
    void whenFindUserByEmailNotFound() {

        Optional<UserEntity> user = userRepository.findByUsername("email@example.com");

        assertThat(user.isPresent()).isFalse();
    }

    @Test
    void whenFindUserByEmailIsSuccess() {

        UserEntity userEntity = new UserEntity(
                null, "email@example.com", "contraseñaMegaSecreta",
                new AuthorEntity(null, "nuevo"), Role.USER
        );

        userRepository.save(userEntity);

        Optional<UserEntity> user = userRepository.findByUsername("email@example.com");

        assertThat(user.isPresent()).isTrue();
        assertThat(user.get().getUsername()).isEqualTo(userEntity.getUsername());
    }
}

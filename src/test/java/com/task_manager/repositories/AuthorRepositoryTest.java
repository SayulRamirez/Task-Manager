package com.task_manager.repositories;

import com.task_manager.entities.AuthorEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;
    private AuthorEntity authorEntity;

    @BeforeEach
    void setup() {
        authorEntity = new AuthorEntity(null, "juan");
    }

    @Test
    void createAuthor() {

        AuthorEntity author = authorRepository.save(authorEntity);

        assertThat(author).isNotNull();
        assertThat(author.getId()).isNotZero();
    }

    @Test
    void findAuthorById() {

        AuthorEntity authorSave = authorRepository.save(authorEntity);

        Optional<AuthorEntity> author = authorRepository.findById(authorSave.getId());

        assertThat(author).isNotNull();
        assertThat(author.isPresent()).isTrue();

        String nick = author.get().getNick();

        assertThat(nick).isEqualTo("juan");

    }

    @Test
    void notFoundAuthorById() {

        Optional<AuthorEntity> author = authorRepository.findById(2L);

        assertThat(author.isEmpty()).isTrue();
    }
}

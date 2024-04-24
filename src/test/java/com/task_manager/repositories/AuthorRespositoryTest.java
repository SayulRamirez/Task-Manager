package com.task_manager.repositories;

import com.task_manager.entities.AuthorEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class AuthorRespositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void createAuthor() {

        AuthorEntity authorEntity = new AuthorEntity(null, "juan");

        AuthorEntity author = authorRepository.save(authorEntity);

        assertThat(author).isNotNull();
        assertThat(author.getId()).isNotZero();
    }
}

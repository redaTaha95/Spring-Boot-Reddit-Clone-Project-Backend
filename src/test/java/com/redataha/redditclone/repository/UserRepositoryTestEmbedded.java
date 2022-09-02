package com.redataha.redditclone.repository;

import com.redataha.redditclone.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTestEmbedded {
    @Autowired
    private UserRepository userRepository;
    private User user, savedUser;

    @BeforeEach
    public void setup() {
        user = new User(null, "test user", "secret password", "user@email.com", Instant.now(), true);
        savedUser = userRepository.save(user);
    }

    @Test
    public void shouldSaveUser() {
        assertThat(savedUser).usingRecursiveComparison().ignoringFields("id").isEqualTo(user);
    }

    @Test
    @Sql("classpath:test-data.sql")
    public void shouldSaveUserThroughSqlFile() {
        Optional<User> test = userRepository.findByUsername("testuser_sql");
        assertThat(test).isNotEmpty();
    }
}

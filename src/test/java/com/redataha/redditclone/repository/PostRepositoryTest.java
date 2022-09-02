package com.redataha.redditclone.repository;

import com.redataha.redditclone.BaseTest;
import com.redataha.redditclone.model.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostRepositoryTest extends BaseTest {
    @Autowired
    private PostRepository postRepository;

    @Test
    public void shouldSavePost() {
        Post expectedPostObject = new Post(null, "First Post", "http://url.site", "Test",
                0, null, Instant.now(), null, null, null, false, false);
        Post actualPostObject = postRepository.save(expectedPostObject);
        Assertions.assertThat(actualPostObject).usingRecursiveComparison()
                .ignoringFields("id").isEqualTo(expectedPostObject);
    }
}

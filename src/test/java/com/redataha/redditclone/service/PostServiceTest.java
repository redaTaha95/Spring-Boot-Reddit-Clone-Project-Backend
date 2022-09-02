package com.redataha.redditclone.service;

import com.redataha.redditclone.dto.PostRequest;
import com.redataha.redditclone.dto.PostResponse;
import com.redataha.redditclone.mapper.PostMapper;
import com.redataha.redditclone.model.Post;
import com.redataha.redditclone.model.Subreddit;
import com.redataha.redditclone.model.User;
import com.redataha.redditclone.repository.PostRepository;
import com.redataha.redditclone.repository.SubredditRepository;
import com.redataha.redditclone.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static java.util.Collections.emptyList;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
//    private PostRepository postRepository = Mockito.mock(PostRepository.class);
//    private SubredditRepository subredditRepository = Mockito.mock(SubredditRepository.class);
//    private UserRepository userRepository = Mockito.mock(UserRepository.class);
//    private AuthService authService = Mockito.mock(AuthService.class);
//    private PostMapper postMapper = Mockito.mock(PostMapper.class);
    @Mock
    private PostRepository postRepository;
    @Mock
    private SubredditRepository subredditRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthService authService;
    @Mock
    private PostMapper postMapper;
    @Captor
    private ArgumentCaptor<Post> postArgumentCaptor;
    private PostService postService;

    @BeforeEach
    public void setup() {
        postService = new PostService(postRepository, subredditRepository, userRepository, authService, postMapper);
    }

    @Test
    @DisplayName("Should Retrive Post by Id")
    public void shouldFindPostById() {
        Post post = new Post(123L, "First Post", "url.com", "Test",
                0, null, Instant.now(), null, null, null, false, false);
        PostResponse expectedPostResponse = new PostResponse(123L, "First Post", "http://url.site", "Test",
                "Test User", "Test Subredit", 0, 0, "1 Hour Ago", false, false);

        Mockito.when(postRepository.findById(123L)).thenReturn(Optional.of(post));
        Mockito.when(postMapper.mapToDto(Mockito.any(Post.class))).thenReturn(expectedPostResponse);

        PostResponse actualPostResponse = postService.getPost(123L);

        Assertions.assertThat(actualPostResponse.getId()).isEqualTo(expectedPostResponse.getId());
        Assertions.assertThat(actualPostResponse.getName()).isEqualTo(expectedPostResponse.getName());
    }

    @Test
    @DisplayName("Should Save Post")
    public void shouldSavePost(){
        User currentUser = new User(123L, "test user", "secret password", "user@email.com", Instant.now(), true);
        Subreddit subreddit = new Subreddit(123L, "First Subreddit", "Subreddit Description", emptyList(), Instant.now(), currentUser);
        Post post = new Post(123L, "First Post", "http://url.site", "Test",
                0, null, Instant.now(), null, null, null, false, false);
        PostRequest postRequest = new PostRequest(null, "First Subreddit", "First Post", "http://url.site", "Test");

        Mockito.when(subredditRepository.findByName("First Subreddit"))
                .thenReturn(Optional.of(subreddit));
        Mockito.when(authService.getCurrentUser())
                .thenReturn(currentUser);
        Mockito.when(postMapper.map(postRequest, subreddit, currentUser))
                .thenReturn(post);

        postService.save(postRequest);
        Mockito.verify(postRepository, Mockito.times(1)).save(postArgumentCaptor.capture());

        Assertions.assertThat(postArgumentCaptor.getValue().getId()).isEqualTo(123L);
        Assertions.assertThat(postArgumentCaptor.getValue().getName()).isEqualTo("First Post");
    }
}

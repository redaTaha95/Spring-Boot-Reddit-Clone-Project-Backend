package com.redataha.redditclone.service;

import com.redataha.redditclone.exceptions.SpringRedditException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CommentServiceTest {

    @Test
    @DisplayName("Test Should Pass When Comment do not Contains Swear words")
    public void shouldNotContainSwearWordsInsideComment() {
        CommentService commentService = new CommentService(null, null, null, null, null, null, null);
        assertThat(commentService.containsSwearWords("This is a comment")).isFalse();
    }

    @Test
    @DisplayName("Should Throw Exception when Comment Contains Swear Words")
    public void shouldFailWhenCommentContainsSwearWords() {
        CommentService commentService = new CommentService(null, null, null, null, null, null, null);
        assertThatThrownBy(() -> {
            commentService.containsSwearWords("This is a shitty comment");
        }).isInstanceOf(SpringRedditException.class)
                .hasMessage("Comment contains unacceptable language");
    }
}

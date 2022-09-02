package com.redataha.redditclone.service;

import com.redataha.redditclone.dto.CommentDto;
import com.redataha.redditclone.exceptions.PostNotFoundException;
import com.redataha.redditclone.exceptions.SpringRedditException;
import com.redataha.redditclone.mapper.CommentMapper;
import com.redataha.redditclone.model.Comment;
import com.redataha.redditclone.model.NotificationEmail;
import com.redataha.redditclone.model.Post;
import com.redataha.redditclone.model.User;
import com.redataha.redditclone.repository.CommentRepository;
import com.redataha.redditclone.repository.PostRepository;
import com.redataha.redditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private CommentMapper commentMapper;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public void save(CommentDto commentDto) {
        Post post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post NOT FOUND!!"));
        Comment comment = commentMapper.map(commentDto, post, authService.getCurrentUser());
        commentRepository.save(comment);

        String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post.");
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(
                new NotificationEmail(user.getUsername() + "  commented on your post",
                        user.getEmail(),
                        message
                )
        );
    }

    public List<CommentDto> getAllCommentsOfPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post NOT FOUND !!"));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(toList());
    }

    public List<CommentDto> getAllCommentsOfUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return commentRepository.findByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(toList());
    }

    public boolean containsSwearWords(String comment) {
        if (comment.contains("shit")) {
            throw new SpringRedditException("Comment contains unacceptable language");
        }
        return false;
    }
}

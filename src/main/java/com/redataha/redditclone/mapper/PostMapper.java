package com.redataha.redditclone.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.redataha.redditclone.dto.PostRequest;
import com.redataha.redditclone.dto.PostResponse;
import com.redataha.redditclone.model.Post;
import com.redataha.redditclone.model.Subreddit;
import com.redataha.redditclone.model.User;
import com.redataha.redditclone.repository.CommentRepository;
import com.redataha.redditclone.repository.VoteRepository;
import com.redataha.redditclone.service.AuthService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PostMapper {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AuthService authService;

    @Mapping(target = "id", source = "postRequest.id")
    @Mapping(target = "name", source = "postRequest.name")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "voteCount", constant = "0")
    public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);

    //We can remove mapping annotation when the target and the source have the same name
    /*@Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "url", source = "url")*/
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    public abstract PostResponse mapToDto(Post post);

    Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }
}

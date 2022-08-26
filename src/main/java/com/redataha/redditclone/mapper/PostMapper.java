package com.redataha.redditclone.mapper;

import com.redataha.redditclone.dto.PostRequest;
import com.redataha.redditclone.dto.PostResponse;
import com.redataha.redditclone.model.Post;
import com.redataha.redditclone.model.Subreddit;
import com.redataha.redditclone.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "id", source = "postRequest.id")
    @Mapping(target = "name", source = "postRequest.name")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    Post map(PostRequest postRequest, Subreddit subreddit, User user);

    //We can remove mapping annotation when the target and the source have the same name
    /*@Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "url", source = "url")*/
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "username", source = "user.username")
    PostResponse mapToDto(Post post);
}

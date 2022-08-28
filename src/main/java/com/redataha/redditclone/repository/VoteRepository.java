package com.redataha.redditclone.repository;

import com.redataha.redditclone.model.Post;
import com.redataha.redditclone.model.User;
import com.redataha.redditclone.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByIdDesc(Post post, User currentUser);
}

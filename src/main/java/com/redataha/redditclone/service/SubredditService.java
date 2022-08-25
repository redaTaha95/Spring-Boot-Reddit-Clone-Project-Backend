package com.redataha.redditclone.service;

import com.redataha.redditclone.dto.SubredditDto;
import com.redataha.redditclone.exceptions.SpringRedditException;
import com.redataha.redditclone.mapper.SubredditMapper;
import com.redataha.redditclone.model.Subreddit;
import com.redataha.redditclone.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {
    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit subreddit = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
        subredditDto.setId(subreddit.getId());
        return subredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(toList());
    }

    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("No reddit found with this id: " + id));
        return subredditMapper.mapSubredditToDto(subreddit);
    }

//    private SubredditDto mapToDo(Subreddit subreddit) {
//        return SubredditDto.builder().name(subreddit.getName())
//                .id(subreddit.getId())
//                .numberOfPosts(subreddit.getPosts().size())
//                .build();
//    }

//    private Subreddit mapSubredditDto(SubredditDto subredditDto) {
//        return Subreddit.builder().name(subredditDto.getName())
//                .description(subredditDto.getDescription())
//                .build();
//    }
}

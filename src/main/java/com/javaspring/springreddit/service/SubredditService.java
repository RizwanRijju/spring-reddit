package com.javaspring.springreddit.service;

import com.javaspring.springreddit.*;
import com.javaspring.springreddit.dto.*;
import com.javaspring.springreddit.exceptions.*;
import com.javaspring.springreddit.mapper.*;
import com.javaspring.springreddit.model.*;
import com.javaspring.springreddit.repository.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;

import javax.transaction.*;
import java.util.*;
import java.util.stream.*;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
        subredditDto.setId(save.getId());
        return subredditDto;
    }

    @Transactional
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(Collectors.toList());
    }

    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("No Subreddit found with the given ID"));
        return subredditMapper.mapSubredditToDto(subreddit);
    }

    /*

    //Methods used to map SubredditDTO to subreddit and Subreddit to SubredditDTO using custom builder.
    //These are replaced with Mapstruct's Mapper interface and its "runtime-implemented" code generators

    private SubredditDto mapToDto(Subreddit subreddit) {
        return SubredditDto.builder().name(subreddit.getName())
                .id(subreddit.getId())
                .description((subreddit.getDescription()))
                .numberOfPosts(subreddit.getPosts().size())
                .build();
    }
    private Subreddit mapSubredditDto(SubredditDto subredditDto) {
        return Subreddit.builder().name(subredditDto.getName())
                .description(subredditDto.getDescription())
                .build();

    }
     */
}

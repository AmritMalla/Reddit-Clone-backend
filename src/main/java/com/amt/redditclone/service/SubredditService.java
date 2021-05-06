package com.amt.redditclone.service;

import com.amt.redditclone.dto.SubredditDto;
import com.amt.redditclone.exceptions.SpringRedditException;
import com.amt.redditclone.mapper.SubredditMapper;
import com.amt.redditclone.model.Subreddit;
import com.amt.redditclone.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by Amrit Malla
 * date : 04/26/2021
 * time : 12:44 AM
 */

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditRepository subRedditRepository;

    private final SubredditMapper subredditMapper;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit save = subRedditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
        subredditDto.setId(save.getId());
        return subredditDto;
    }


    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subRedditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(Collectors.toList());
    }


    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subRedditRepository.findById(id)
                .orElseThrow(() ->
                        new SpringRedditException("No subreddit found with id : " + id));
        return subredditMapper.mapSubredditToDto(subreddit);
    }
}

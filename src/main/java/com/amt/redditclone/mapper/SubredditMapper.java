package com.amt.redditclone.mapper;

import com.amt.redditclone.dto.SubredditDto;
import com.amt.redditclone.model.Subreddit;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * Created by Amrit Malla
 * date : 04/26/2021
 * time : 12:41 PM
 */

@Component
public class SubredditMapper {

    //    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    public SubredditDto mapSubredditToDto(Subreddit subreddit) {
        if (subreddit == null)
            return null;
        return SubredditDto.builder()
                .id(subreddit.getId())
                .name(subreddit.getName())
                .description(subreddit.getDescription())
                .numberOfPosts(subreddit.getPosts().size())
                .build();
    }

    public Subreddit mapDtoToSubreddit(SubredditDto dto) {
        if (dto == null) {
            return null;
        }
        return Subreddit.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .createdDate(Instant.now())
                .build();
    }
}

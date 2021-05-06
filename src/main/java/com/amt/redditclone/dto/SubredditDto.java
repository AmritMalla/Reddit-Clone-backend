package com.amt.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Amrit Malla
 * date : 04/26/2021
 * time : 12:41 AM
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubredditDto {

    private Long id;

    private String name;

    private String description;

    private Integer numberOfPosts;
}

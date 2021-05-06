package com.amt.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Amrit Malla
 * date : 04/26/2021
 * time : 4:20 PM
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {

    private Long id;

    private String name;

    private String url;

    private String description;

    private String username;

    private String subredditName;

    private Integer voteCount;

    private Integer commentCount;

    private String duration;

    private boolean upVote;

    private boolean downVote;
}

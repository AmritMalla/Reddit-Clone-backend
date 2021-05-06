package com.amt.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Created by Amrit Malla
 * date : 04/27/2021
 * time : 8:29 PM
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {

    private Long id;
    private Long postId;
    private Instant createdDate;
    private String  text;
    private String username;

}

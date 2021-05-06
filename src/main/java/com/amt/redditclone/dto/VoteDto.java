package com.amt.redditclone.dto;

import com.amt.redditclone.model.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Amrit Malla
 * date : 04/28/2021
 * time : 7:33 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {

    private VoteType voteType;

    private Long postId;
}

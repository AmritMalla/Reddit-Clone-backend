package com.amt.redditclone.controller;

import com.amt.redditclone.dto.VoteDto;
import com.amt.redditclone.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Amrit Malla
 * date : 04/28/2021
 * time : 7:27 PM
 */

@RestController
@RequestMapping("/api/votes")
@AllArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<String> vote(@RequestBody VoteDto voteDto){
        voteService.vote(voteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully Voted");
    }
}

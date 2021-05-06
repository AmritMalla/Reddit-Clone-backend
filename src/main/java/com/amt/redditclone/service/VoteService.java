package com.amt.redditclone.service;

import com.amt.redditclone.dto.VoteDto;
import com.amt.redditclone.exceptions.PostNotFoundException;
import com.amt.redditclone.exceptions.SpringRedditException;
import com.amt.redditclone.model.Post;
import com.amt.redditclone.model.User;
import com.amt.redditclone.model.Vote;
import com.amt.redditclone.model.VoteType;
import com.amt.redditclone.repository.PostRepository;
import com.amt.redditclone.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by Amrit Malla
 * date : 04/28/2021
 * time : 7:30 PM
 */
@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;

    private final PostRepository postRepository;

    private final AuthService authService;

    @Transactional(readOnly = true)
    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post not found with ID - " + voteDto.getPostId()));
        User currentUser = authService.getCurrentUser();
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, currentUser);
        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
            throw new SpringRedditException("You have already " + voteDto.getVoteType() + "'d for this post");
        }

        if (VoteType.UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }

        voteRepository.save(mapToVote(voteDto, post, currentUser));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post, User currentUser) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(currentUser)
                .build();
    }

}

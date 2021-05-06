package com.amt.redditclone.mapper;

import com.amt.redditclone.dto.PostRequest;
import com.amt.redditclone.dto.PostResponse;
import com.amt.redditclone.model.*;
import com.amt.redditclone.repository.CommentRepository;
import com.amt.redditclone.repository.SubredditRepository;
import com.amt.redditclone.repository.VoteRepository;
import com.amt.redditclone.service.AuthService;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

/**
 * Created by Amrit Malla
 * date : 04/26/2021
 * time : 4:14 PM
 */

@Component
public class PostMapper {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private SubredditRepository subredditRepository;

    @Autowired
    private AuthService authService;

    public Post map(PostRequest postRequest, Subreddit subreddit, User user) {

        if (postRequest == null && subreddit == null && user == null)
            return null;
        Post.PostBuilder builder = Post.builder();
        if (postRequest != null) {
            builder.description(postRequest.getDescription())
                    .id(postRequest.getId())
                    .name(postRequest.getName())
                    .url(postRequest.getName());

        }
        if (subreddit != null) {
            builder.subreddit(subreddit);
            builder.user(subreddit.getUser());
        }
        builder.createdDate(Instant.now())
                .voteCount(0);
        return builder.build();
    }

    /*@Mapping(target = "id", source = "id")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    @Mapping(target = "upVote", expression = "java(isPostUpVoted(post))")
    @Mapping(target = "downVote", expression = "java(isPostDownVoted(post))")
    */
    public PostResponse mapToDto(Post post) {
        if (post == null)
            return null;
        PostResponse.PostResponseBuilder builder = PostResponse.builder();
        builder.id(post.getId())
                .name(post.getName())
                .url(post.getUrl())
                .description(post.getDescription())
                .commentCount(commentCount(post))
                .voteCount(post.getVoteCount())
                .duration(getDuration(post))
                .upVote(isPostUpVoted(post))
                .downVote((isPostDownVoted(post)));
        if (post.getSubreddit() != null) {
            builder.subredditName(post.getSubreddit().getName());
        }
        if (post.getUser() != null) {
            builder.username(post.getUser().getUsername());
        }

        return builder.build();
    }


    Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }

    boolean isPostUpVoted(Post post) {
        return checkVoteType(post, VoteType.UPVOTE);
    }

    boolean isPostDownVoted(Post post) {
        return checkVoteType(post, VoteType.DOWNVOTE);
    }

    private boolean checkVoteType(Post post, VoteType voteType) {
        if (authService.isLoggedIn()) {
            Optional<Vote> voteForPostByUser =
                    voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
                            authService.getCurrentUser());
            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
                    .isPresent();
        }
        return false;
    }

}
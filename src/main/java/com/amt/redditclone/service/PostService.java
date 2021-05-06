package com.amt.redditclone.service;

import com.amt.redditclone.dto.PostRequest;
import com.amt.redditclone.dto.PostResponse;
import com.amt.redditclone.exceptions.PostNotFoundException;
import com.amt.redditclone.exceptions.SubredditNotFoundException;
import com.amt.redditclone.mapper.PostMapper;
import com.amt.redditclone.model.Post;
import com.amt.redditclone.model.Subreddit;
import com.amt.redditclone.model.User;
import com.amt.redditclone.repository.PostRepository;
import com.amt.redditclone.repository.SubredditRepository;
import com.amt.redditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Amrit Malla
 * date : 04/26/2021
 * time : 4:08 PM
 */

@Service
@AllArgsConstructor
@Slf4j
public class PostService {

    private final SubredditRepository subRedditRepository;

    private final AuthService authService;

    private final PostMapper postMapper;

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    @Transactional
    public Long save(PostRequest postRequest) {
        Subreddit subreddit = subRedditRepository.findByName(postRequest.getSubredditName()).orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
        User currentUser = authService.getCurrentUser();

        return postRepository.save(postMapper.map(postRequest, subreddit, currentUser)).getId();

    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new PostNotFoundException("Post with id " + id.toString() + " not found"));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
        Subreddit subreddit = subRedditRepository.findById(subredditId)
                .orElseThrow(() -> new SubredditNotFoundException("Subreddit with id  : " + subredditId + " not found"));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(postMapper::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findAllByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

}

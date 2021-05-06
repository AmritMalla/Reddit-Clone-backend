package com.amt.redditclone.service;

import com.amt.redditclone.dto.CommentDto;
import com.amt.redditclone.exceptions.PostNotFoundException;
import com.amt.redditclone.mapper.CommentMapper;
import com.amt.redditclone.model.Comment;
import com.amt.redditclone.model.NotificationEmail;
import com.amt.redditclone.model.Post;
import com.amt.redditclone.model.User;
import com.amt.redditclone.repository.CommentRepository;
import com.amt.redditclone.repository.PostRepository;
import com.amt.redditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Amrit Malla
 * date : 04/27/2021
 * time : 8:31 PM
 */
@Service
@AllArgsConstructor
public class CommentService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final AuthService authService;

    private final CommentMapper commentMapper;

    private final CommentRepository commentRepository;

    private final MailContentBuilder mailContentBuilder;

    private final MailService mailService;

    public Long save(CommentDto commentDto) {
        Post post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentDto.getPostId().toString()));

        Comment comment = commentMapper.map(commentDto, post, authService.getCurrentUser());
        Long save = commentRepository.save(comment).getId();
        sendNotification(commentNotificationMessageBuilder(post), authService.getCurrentUser());
        return save;

    }

    public String commentNotificationMessageBuilder(Post post) {
        return mailContentBuilder.build(authService.getCurrentUser().getUsername()+ " posted a comment on your post " + post.getUrl());
    }

    private void sendNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " commented on your post", user.getEmail(), message));
    }

    @Transactional
    public List<CommentDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<CommentDto> getAllCommentsForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException(username + " not found"));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }
}

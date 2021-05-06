package com.amt.redditclone.mapper;

import com.amt.redditclone.dto.CommentDto;
import com.amt.redditclone.model.Comment;
import com.amt.redditclone.model.Post;
import com.amt.redditclone.model.User;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Created by Amrit Malla
 * date : 04/27/2021
 * time : 8:36 PM
 */
@Component
public class CommentMapper {

    public Comment map(CommentDto commentDto, Post post, User user) {
        return Comment.builder()
                .text(commentDto.getText())
                .createdDate(Instant.now())
                .post(post)
                .user(user)
                .build();
    }

    public CommentDto mapToDto(Comment comment) {

        if (comment == null) {
            return null;
        }
        CommentDto.CommentDtoBuilder builder = CommentDto.builder();

        builder.id(comment.getId())
                .createdDate(comment.getCreatedDate())
                .text(comment.getText());
        if (comment.getPost() != null) {
            builder.postId(comment.getPost().getId());
        }

        if (comment.getUser() != null) {
            builder.username(comment.getUser().getUsername());
        }
        return builder.build();
    }
}

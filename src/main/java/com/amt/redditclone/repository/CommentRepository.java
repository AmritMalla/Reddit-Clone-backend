package com.amt.redditclone.repository;

import com.amt.redditclone.model.Comment;
import com.amt.redditclone.model.Post;
import com.amt.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Amrit Malla
 * date : 04/23/2021
 * time : 1:12 PM
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}

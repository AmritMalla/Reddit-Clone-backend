package com.amt.redditclone.repository;

import com.amt.redditclone.model.Post;
import com.amt.redditclone.model.User;
import com.amt.redditclone.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Amrit Malla
 * date : 04/23/2021
 * time : 1:12 PM
 */
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);

}

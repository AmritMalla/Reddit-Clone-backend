package com.amt.redditclone.repository;

import com.amt.redditclone.model.Post;
import com.amt.redditclone.model.Subreddit;
import javafx.geometry.Pos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Amrit Malla
 * date : 04/23/2021
 * time : 1:12 PM
 */
public interface SubredditRepository extends JpaRepository<Subreddit, Long> {

    Optional<Subreddit> findByName(String name);

}

package com.amt.redditclone.repository;

import com.amt.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Amrit Malla
 * date : 04/23/2021
 * time : 1:12 PM
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}

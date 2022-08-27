package com.javaspring.springreddit.repository;

import com.javaspring.springreddit.model.*;
import com.javaspring.springreddit.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.Optional;

@Repository
public interface SubredditRepository extends JpaRepository<Subreddit, Long> {

    Optional<Subreddit> findByName(String subredditName);
}

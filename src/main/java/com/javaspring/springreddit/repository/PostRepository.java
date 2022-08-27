package com.javaspring.springreddit.repository;

import com.javaspring.springreddit.model.*;
import com.javaspring.springreddit.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.Collection;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllBySubreddit(Subreddit subreddit);

    Collection<Post> findByUser(User user);
}

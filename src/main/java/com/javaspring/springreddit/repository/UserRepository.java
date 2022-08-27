package com.javaspring.springreddit.repository;

import com.javaspring.springreddit.model.*;
import com.javaspring.springreddit.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}

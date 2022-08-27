package com.javaspring.springreddit.mapper;

import com.javaspring.springreddit.dto.*;
import com.javaspring.springreddit.model.*;

import org.mapstruct.*;


import java.util.*;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    SubredditDto mapSubredditToDto(Subreddit subreddit);

     default Integer mapPosts(List<Post> posts){
         return posts.size();
     }

     @InheritInverseConfiguration
     @Mapping(target = "posts", ignore = true)
     Subreddit mapDtoToSubreddit(SubredditDto subredditDto);

}
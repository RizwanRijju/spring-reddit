package com.javaspring.springreddit.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.javaspring.springreddit.dto.CommentsDto;
import com.javaspring.springreddit.model.Comment;
import com.javaspring.springreddit.model.Post;
import com.javaspring.springreddit.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentsDto.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")

    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    public abstract Comment map(CommentsDto commentsDto, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
    @Mapping(target = "duration", expression = "java(getDuration(comment))")
    public abstract CommentsDto mapToDto(Comment comment);


    String getDuration(Comment comment){
        return TimeAgo.using(comment.getCreatedDate().toEpochMilli());
    }
}

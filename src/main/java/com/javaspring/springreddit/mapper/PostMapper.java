package com.javaspring.springreddit.mapper;


import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.javaspring.springreddit.dto.PostRequest;
import com.javaspring.springreddit.dto.PostResponse;
import com.javaspring.springreddit.model.*;
import com.javaspring.springreddit.repository.CommentRepository;
import com.javaspring.springreddit.repository.VoteRepository;
import com.javaspring.springreddit.service.AuthService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.javaspring.springreddit.model.VoteType.DOWNVOTE;
import static com.javaspring.springreddit.model.VoteType.UPVOTE;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AuthService authService;

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")

    /* Variables having same name in Post and PostRequest don't need exclusive mapping like above.
    But for arguments passed in the map method call like subreddit and user, even though the names are same (user, user), they still
    need to mapped like below. This is because they are passed as arguments and are not a part of "PostRequest"
     */
    @Mapping(target = "user", source = "user")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "voteCount", constant = "0")
    public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);


    @Mapping(target = "id", source = "postId")
    //Below are mapping with same names from PostResponse and Post. So can be completely omitted.
//    @Mapping(target = "description", source = "description")
//    @Mapping(target = "url", source = "url")
//    @Mapping(target = "postName", source = "postName")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    //We could have ignored this mapping completely, but post.getVoteCount() is returning null when it should return "0".
    @Mapping(target = "voteCount", expression = "java(getVoteCount(post))")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    @Mapping(target = "upVote", expression = "java(isPostUpVoted(post))")
    @Mapping(target = "downVote", expression = "java(isPostDownVoted(post))")
    public abstract PostResponse mapToDto(Post post);

    Integer commentCount(Post post){
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post){
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }
    Integer getVoteCount(Post post){
        if(post.getVoteCount() == null)
            return 0;
        return post.getVoteCount();
    }

    boolean isPostUpVoted(Post post) {
        return checkVoteType(post, UPVOTE);
    }

    boolean isPostDownVoted(Post post) {
        return checkVoteType(post, DOWNVOTE);
    }

    private boolean checkVoteType(Post post, VoteType voteType) {
        if (authService.isLoggedIn()) {
            Optional<Vote> voteForPostByUser =
                    voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
                            authService.getCurrentUser());
            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
                    .isPresent();
        }
        return false;
    }
}

package com.javaspring.springreddit.service;

import com.javaspring.springreddit.dto.CommentsDto;
import com.javaspring.springreddit.exceptions.PostNotFoundException;
import com.javaspring.springreddit.mapper.CommentMapper;
import com.javaspring.springreddit.model.Comment;
import com.javaspring.springreddit.model.NotificationEmail;
import com.javaspring.springreddit.model.Post;
import com.javaspring.springreddit.model.User;
import com.javaspring.springreddit.repository.CommentRepository;
import com.javaspring.springreddit.repository.PostRepository;
import com.javaspring.springreddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class CommentService {

    private static final String POST_URL = "";
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailService mailService;
    private final MailContentBuilder mailContentBuilder;


    public void save(CommentsDto commentsDto){
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("No post found with ID - " + commentsDto.getPostId().toString()));

        Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
        commentRepository.save(comment);

        //Sending a mail to the creator of the post saying someone has commented on your post.
        //The current user has posted a comment and so the build method must have current user's username
        String message = mailContentBuilder.build(authService.getCurrentUser().getUsername() + " posted a comment on your post." + POST_URL);
        //Same is the logic here
        sendCommentNotification(message, authService.getCurrentUser(), post);

    }

    //The post is written by post.getUser().getEmail() and so must receive the CommentNotification.
    private void sendCommentNotification(String message, User user, Post post) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", post.getUser().getEmail() , message));
    }


    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("No post found with the ID - " + postId));
        return commentRepository.findByPost(post).stream().map(commentMapper::mapToDto).collect(toList());
    }

    public List<CommentsDto> getAllCommentsForUser(String userName) {

        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("No such username found - " + userName));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(toList());
    }
}

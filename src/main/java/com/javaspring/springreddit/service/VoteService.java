package com.javaspring.springreddit.service;

import com.javaspring.springreddit.dto.VoteDto;
import com.javaspring.springreddit.exceptions.PostNotFoundException;
import com.javaspring.springreddit.exceptions.SpringRedditException;
import com.javaspring.springreddit.model.Post;
import com.javaspring.springreddit.model.Vote;
import com.javaspring.springreddit.repository.PostRepository;
import com.javaspring.springreddit.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.javaspring.springreddit.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {


    private final PostRepository postRepository;
    private final VoteRepository voteRepository;
    private final AuthService authService;

    @Transactional
    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("No post found with ID - " + voteDto.getPostId()));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());


        if(voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType()
                    .equals(voteDto.getVoteType())){
            throw new SpringRedditException("You have already " + voteDto.getVoteType() + "'d for this post");
        }

        //This is needed because voteCount(Integer type) is returning null while we need a "int" zero
        int currentVoteCount = 0;
        if(post.getVoteCount() != null)
            currentVoteCount = post.getVoteCount();

        if(UPVOTE.equals(voteDto.getVoteType()))
            post.setVoteCount(currentVoteCount + 1);
        else
            post.setVoteCount(currentVoteCount - 1);
        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}

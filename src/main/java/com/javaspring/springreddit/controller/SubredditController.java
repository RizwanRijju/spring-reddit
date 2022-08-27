package com.javaspring.springreddit.controller;

import com.javaspring.springreddit.dto.*;
import com.javaspring.springreddit.service.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/subreddit")
public class SubredditController {

    private final SubredditService subredditService;


    @PostMapping
    public void createSubreddit(@RequestBody SubredditDto subredditDto){
        ResponseEntity.status(HttpStatus.CREATED)
                .body(subredditService.save(subredditDto));
    }

    @GetMapping
    public ResponseEntity<List<SubredditDto>> getAllSubreddits(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subredditService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubredditDto> getSubreddit(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subredditService.getSubreddit(id));
    }

}

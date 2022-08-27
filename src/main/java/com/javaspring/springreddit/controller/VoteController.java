package com.javaspring.springreddit.controller;

import com.javaspring.springreddit.dto.VoteDto;
import com.javaspring.springreddit.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/votes")
public class VoteController {

    private final VoteService voteservice;

    @PostMapping
    public ResponseEntity<Void> vote(@RequestBody VoteDto voteDto){
        voteservice.vote(voteDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

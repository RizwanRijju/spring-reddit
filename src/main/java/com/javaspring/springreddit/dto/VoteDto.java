package com.javaspring.springreddit.dto;

import com.javaspring.springreddit.model.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VoteDto {
    private VoteType voteType;
    private Long postId;
}

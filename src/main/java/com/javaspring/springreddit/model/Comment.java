package com.javaspring.springreddit.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.time.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @NotEmpty
    private String text;
    @ManyToOne
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    private Post post;
    private Instant createdDate;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
}

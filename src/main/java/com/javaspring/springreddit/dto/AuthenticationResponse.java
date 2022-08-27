package com.javaspring.springreddit.dto;

import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {

    private String authenticationToken;
    private String username;
    private Instant expiresAt;
    private String refreshToken;
}

package com.complejolapasionaria.reservation.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponseDto {
    private String username;
    private String jwt;
}

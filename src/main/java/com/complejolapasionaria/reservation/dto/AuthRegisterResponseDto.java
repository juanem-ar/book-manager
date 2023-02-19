package com.complejolapasionaria.reservation.dto;

import lombok.Data;

@Data
public class AuthRegisterResponseDto{
    private UserResponseDto userResponseDto;
    private String jwt;
}

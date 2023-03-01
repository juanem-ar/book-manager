package com.reservation.manager.dto;

import lombok.Data;

@Data
public class AuthRegisterResponseDto{
    private UserResponseDto userResponseDto;
    private String jwt;
}

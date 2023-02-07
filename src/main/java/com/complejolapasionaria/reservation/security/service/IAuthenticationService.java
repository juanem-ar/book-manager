package com.complejolapasionaria.reservation.security.service;

import com.complejolapasionaria.reservation.dto.AuthenticationRequestUserDto;
import com.complejolapasionaria.reservation.dto.AuthenticationResponseDto;
import com.complejolapasionaria.reservation.dto.RequestUserDto;
import com.complejolapasionaria.reservation.dto.ResponseUserDto;

public interface IAuthenticationService {
    ResponseUserDto saveUser(RequestUserDto dto) throws  Exception;
    AuthenticationResponseDto logIn(AuthenticationRequestUserDto dto) throws  Exception;
}

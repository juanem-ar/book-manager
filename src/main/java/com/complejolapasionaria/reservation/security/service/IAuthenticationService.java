package com.complejolapasionaria.reservation.security.service;

import com.complejolapasionaria.reservation.dto.auth.AuthenticationRequestUserDto;
import com.complejolapasionaria.reservation.dto.auth.AuthenticationResponseDto;
import com.complejolapasionaria.reservation.dto.RequestUserDto;
import com.complejolapasionaria.reservation.dto.AuthRegisterResponseDto;

public interface IAuthenticationService {
    AuthRegisterResponseDto saveUser(RequestUserDto dto) throws  Exception;
    AuthenticationResponseDto authenticate(AuthenticationRequestUserDto dto) throws  Exception;
}

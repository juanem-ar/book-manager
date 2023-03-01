package com.reservation.manager.security.service;

import com.reservation.manager.dto.auth.AuthenticationRequestUserDto;
import com.reservation.manager.dto.auth.AuthenticationResponseDto;
import com.reservation.manager.dto.RequestUserDto;
import com.reservation.manager.dto.AuthRegisterResponseDto;

public interface IAuthenticationService {
    AuthRegisterResponseDto saveUser(RequestUserDto dto) throws  Exception;
    AuthenticationResponseDto authenticate(AuthenticationRequestUserDto dto) throws  Exception;
}

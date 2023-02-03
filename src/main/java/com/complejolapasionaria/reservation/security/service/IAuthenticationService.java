package com.complejolapasionaria.reservation.security.service;

import com.complejolapasionaria.reservation.dto.AuthenticationRequestUserDto;
import com.complejolapasionaria.reservation.dto.RequestUserDto;

public interface IAuthenticationService {
    void saveUser(RequestUserDto dto) throws  Exception;
    void logIn(AuthenticationRequestUserDto dto) throws  Exception;
}

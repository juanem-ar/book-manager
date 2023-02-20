package com.complejolapasionaria.reservation.service;

import com.complejolapasionaria.reservation.dto.UserResponseDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    UserDetails loadUserByUsername(String email);
    UserResponseDto getUserById(Authentication authentication);
    String removeUserByAuth(Authentication authentication);
}

package com.reservation.manager.service;

import com.reservation.manager.dto.RequestPatchUserDto;
import com.reservation.manager.dto.UserResponseDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    UserDetails loadUserByUsername(String email);
    UserResponseDto getUserById(Authentication authentication);
    String removeUserByAuth(Authentication authentication);

    UserResponseDto updateUser(RequestPatchUserDto dto, Authentication authentication);

}

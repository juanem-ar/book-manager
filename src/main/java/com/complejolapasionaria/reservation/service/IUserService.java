package com.complejolapasionaria.reservation.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    UserDetails loadUserByUsername(String email);
}

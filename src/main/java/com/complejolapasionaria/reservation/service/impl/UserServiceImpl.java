package com.complejolapasionaria.reservation.service.impl;

import com.complejolapasionaria.reservation.dto.UserResponseDto;
import com.complejolapasionaria.reservation.mapper.UserMapper;
import com.complejolapasionaria.reservation.model.User;
import com.complejolapasionaria.reservation.repository.IUserRepository;
import com.complejolapasionaria.reservation.service.IUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    private final IUserRepository iUserRepository;
    private final UserMapper iUserMapper;

    public UserServiceImpl(IUserRepository iUserRepository, UserMapper iUserMapper) {
        this.iUserRepository = iUserRepository;
        this.iUserMapper = iUserMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userEntity = iUserRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException("username or password not found");
        }
        return userEntity;
    }

    @Override
    public UserResponseDto getUserById(Authentication authentication){
        User entity = (User) loadUserByUsername(authentication.getName());
        return iUserMapper.toUserResponseDto(entity);
    }
}

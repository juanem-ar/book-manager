package com.complejolapasionaria.reservation.service.impl;

import com.complejolapasionaria.reservation.dto.RequestPatchUserDto;
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

    @Override
    public String removeUserByAuth(Authentication authentication) {
        User entity = (User) loadUserByUsername(authentication.getName());
        entity.setCredentialsNonExpired(Boolean.FALSE);
        entity.setAccountNonLocked(Boolean.FALSE);
        entity.setAccountNonExpired(Boolean.FALSE);
        entity.setDeleted(Boolean.TRUE);
        iUserRepository.save(entity);
        return "/auth/login";
    }

    @Override
    public UserResponseDto updateUser(RequestPatchUserDto dto, Authentication authentication){
        User entity = iUserRepository.findByEmail(authentication.getName());
        entity.setLastName(dto.getLastName());
        entity.setFirstName(dto.getFirstName());
        entity.setEmail(dto.getEmail());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setDateOfBirth(dto.getDateOfBirth());
        entity.setAddress(dto.getAddress());
        iUserRepository.save(entity);
        return iUserMapper.toUserResponseDto(entity);
    }
}

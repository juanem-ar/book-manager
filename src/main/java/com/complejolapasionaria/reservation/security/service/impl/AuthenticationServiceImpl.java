package com.complejolapasionaria.reservation.security.service.impl;

import com.complejolapasionaria.reservation.dto.AuthenticationRequestUserDto;
import com.complejolapasionaria.reservation.dto.AuthenticationResponseDto;
import com.complejolapasionaria.reservation.dto.RequestUserDto;
import com.complejolapasionaria.reservation.dto.ResponseUserDto;
import com.complejolapasionaria.reservation.exceptions.BadRequestException;
import com.complejolapasionaria.reservation.mapper.UserMapper;
import com.complejolapasionaria.reservation.model.User;
import com.complejolapasionaria.reservation.repository.IRoleRepository;
import com.complejolapasionaria.reservation.repository.IUserRepository;
import com.complejolapasionaria.reservation.security.config.PasswordEncoder;
import com.complejolapasionaria.reservation.security.service.IAuthenticationService;
import com.complejolapasionaria.reservation.service.IUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {
    private final IUserRepository iUserRepository;
    private final UserMapper UserMapper;
    private final IRoleRepository iRoleRepository;
    private final IUserService iUserService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtilsImpl jwtUtils;
    public AuthenticationServiceImpl(IUserRepository iUserRepository, UserMapper UserMapper, IRoleRepository iRoleRepository, IUserService iUserService, PasswordEncoder passwordEncoder, JwtUtilsImpl jwtUtils) {
        this.iUserRepository = iUserRepository;
        this.UserMapper = UserMapper;
        this.iRoleRepository = iRoleRepository;
        this.iUserService = iUserService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public ResponseUserDto saveUser(RequestUserDto dto) throws Exception {
        if(iUserRepository.existsByEmail(dto.getEmail()))
            throw new UsernameNotFoundException("There is an account with that email address.");
        User entity = UserMapper.toEntity(dto);
        User entitySaved = iUserRepository.save(entity);
        return UserMapper.toResponseUserDto(entitySaved);
    }

    @Override
    public AuthenticationResponseDto logIn(AuthenticationRequestUserDto dto) throws BadRequestException {
        String username = dto.getEmail();
        String password = dto.getPassword();
        //TODO revisar validacion de email vacio
        if(!iUserRepository.existsByEmail(username)){
            throw new BadRequestException("There isn't an account with that email " + username);
        }
        if (!passwordEncoder.bCryptPasswordEncoder().matches(password,iUserRepository.findByEmail(username).getPassword())){
            throw new BadRequestException("Incorrect password");
        }
        final UserDetails userDetails = iUserService.loadUserByUsername(username);
        System.out.println( userDetails.toString());
        final String jwt = jwtUtils.generateToken(userDetails);
        return new AuthenticationResponseDto(username,jwt);
    }
}

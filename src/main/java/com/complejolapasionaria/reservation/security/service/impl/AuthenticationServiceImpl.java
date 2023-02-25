package com.complejolapasionaria.reservation.security.service.impl;

import com.complejolapasionaria.reservation.dto.auth.AuthenticationRequestUserDto;
import com.complejolapasionaria.reservation.dto.auth.AuthenticationResponseDto;
import com.complejolapasionaria.reservation.dto.RequestUserDto;
import com.complejolapasionaria.reservation.dto.AuthRegisterResponseDto;
import com.complejolapasionaria.reservation.exceptions.BadRequestException;
import com.complejolapasionaria.reservation.mapper.UserMapper;
import com.complejolapasionaria.reservation.model.User;
import com.complejolapasionaria.reservation.security.service.IAuthenticationService;
import com.complejolapasionaria.reservation.repository.IRoleRepository;
import com.complejolapasionaria.reservation.repository.IUserRepository;
import com.complejolapasionaria.reservation.security.config.PasswordEncoder;
import com.complejolapasionaria.reservation.service.IUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public AuthRegisterResponseDto saveUser(RequestUserDto dto) throws Exception {
        if(iUserRepository.existsByEmail(dto.getEmail()))
            throw new UsernameNotFoundException("There is an account with that email address.");
        User entity = UserMapper.toEntity(dto);
        User entitySaved = iUserRepository.save(entity);

        AuthRegisterResponseDto response = new AuthRegisterResponseDto();
        response.setUserResponseDto(UserMapper.toUserResponseDto(entitySaved));
        AuthenticationRequestUserDto authRequest = new AuthenticationRequestUserDto(dto.getEmail(),dto.getPassword());
        AuthenticationResponseDto authResponse = logIn(authRequest);
        response.setJwt(authResponse.getJwt());
        return response;
    }

    @Override
    public AuthenticationResponseDto logIn(AuthenticationRequestUserDto dto) throws BadRequestException {
        String username = dto.getEmail();
        String password = dto.getPassword();
        if(!iUserRepository.existsByEmail(username)){
            throw new UsernameNotFoundException("There isn't an account with that email " + username);
        }
        if (!passwordEncoder.bCryptPasswordEncoder().matches(password,iUserRepository.findByEmail(username).getPassword())){
            throw new BadRequestException("Incorrect password");
        }
        final UserDetails userDetails = iUserService.loadUserByUsername(username);
        final String jwt = jwtUtils.generateToken(userDetails);
        return new AuthenticationResponseDto(username,jwt);
    }
}

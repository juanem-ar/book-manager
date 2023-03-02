package com.reservation.manager.security.service.impl;

import com.reservation.manager.Enum.ETokenType;
import com.reservation.manager.dto.auth.AuthenticationRequestUserDto;
import com.reservation.manager.dto.auth.AuthenticationResponseDto;
import com.reservation.manager.dto.RequestUserDto;
import com.reservation.manager.dto.AuthRegisterResponseDto;
import com.reservation.manager.mapper.UserMapper;
import com.reservation.manager.model.Token;
import com.reservation.manager.model.User;
import com.reservation.manager.repository.ITokenRepository;
import com.reservation.manager.security.service.IAuthenticationService;
import com.reservation.manager.repository.IUserRepository;
import com.reservation.manager.service.IEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {
    private final IUserRepository iUserRepository;
    private final UserMapper UserMapper;
    private final JwtUtilsImpl jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final ITokenRepository iTokenRepository;
    private final IEmailService iEmailService;

    @Override
    @Transactional
    public AuthRegisterResponseDto saveUser(RequestUserDto dto) throws Exception {
        emailValidator(dto.getEmail(),false);
        User entity = UserMapper.toEntity(dto);
        User entitySaved = iUserRepository.save(entity);
        AuthRegisterResponseDto response = new AuthRegisterResponseDto();
        response.setUserResponseDto(UserMapper.toUserResponseDto(entitySaved));
        AuthenticationRequestUserDto authRequest = new AuthenticationRequestUserDto(dto.getEmail(),dto.getPassword());
        AuthenticationResponseDto authResponse = authenticate(authRequest);
        response.setJwt(authResponse.getJwt());
        iEmailService.sendWelcomeEmailTo(authResponse.getUsername());
        return response;
    }
    public AuthenticationResponseDto authenticate(AuthenticationRequestUserDto request) throws IOException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));
        var user = iUserRepository.findById(iUserRepository.findByEmail(request.getEmail()).getId())
                .orElseThrow();
        var jwt = jwtUtils.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user,jwt);
        return new AuthenticationResponseDto(user.getUsername(),jwt);
    }
    private void saveUserToken(User user, String jwt) {
        var token = Token.builder()
                .user(user)
                .token(jwt)
                .tokenType(ETokenType.BEARER)
                .expired(false)
                .expired(false)
                .build();
        iTokenRepository.save(token);
    }
    public void revokeAllUserTokens(User user){
        var validToken = iTokenRepository.findAllValidTokensByUser(user.getId());
        if (validToken.isEmpty()){
            return;
        }
        validToken.forEach(t->{
            t.setExpired(true);
            t.setRevoked(true);
                });
        iTokenRepository.saveAll(validToken);
    }
    public void emailValidator(String email, Boolean isUser) {
        if (isUser) {
            if (!iUserRepository.existsByEmail(email))
                throw new UsernameNotFoundException("There isn't an account with that email " + email);
        } else {
            if (iUserRepository.existsByEmail(email))
                throw new UsernameNotFoundException("There is an account with that email address.");
        }
    }
}

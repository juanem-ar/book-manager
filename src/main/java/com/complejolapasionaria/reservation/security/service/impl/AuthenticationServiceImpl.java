package com.complejolapasionaria.reservation.security.service.impl;

import com.complejolapasionaria.reservation.Enum.ETokenType;
import com.complejolapasionaria.reservation.dto.auth.AuthenticationRequestUserDto;
import com.complejolapasionaria.reservation.dto.auth.AuthenticationResponseDto;
import com.complejolapasionaria.reservation.dto.RequestUserDto;
import com.complejolapasionaria.reservation.dto.AuthRegisterResponseDto;
import com.complejolapasionaria.reservation.mapper.UserMapper;
import com.complejolapasionaria.reservation.model.Token;
import com.complejolapasionaria.reservation.model.User;
import com.complejolapasionaria.reservation.repository.ITokenRepository;
import com.complejolapasionaria.reservation.security.service.IAuthenticationService;
import com.complejolapasionaria.reservation.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {
    private final IUserRepository iUserRepository;
    private final UserMapper UserMapper;
    private final JwtUtilsImpl jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final ITokenRepository iTokenRepository;

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
        return response;
    }
    public AuthenticationResponseDto authenticate(AuthenticationRequestUserDto request){
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

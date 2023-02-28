package com.complejolapasionaria.reservation.security.controller;

import com.complejolapasionaria.reservation.dto.auth.AuthenticationRequestUserDto;
import com.complejolapasionaria.reservation.dto.auth.AuthenticationResponseDto;
import com.complejolapasionaria.reservation.dto.RequestUserDto;
import com.complejolapasionaria.reservation.dto.AuthRegisterResponseDto;
import com.complejolapasionaria.reservation.security.service.IAuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Register and Login to use the app")
@RequiredArgsConstructor
public class UserAuthController extends
        SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {

    private final IAuthenticationService iAuthenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthRegisterResponseDto> signUp(@Valid @RequestBody RequestUserDto dto) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(iAuthenticationService.saveUser(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> signIn(@Valid @RequestBody AuthenticationRequestUserDto dto) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iAuthenticationService.authenticate(dto));
    }
}

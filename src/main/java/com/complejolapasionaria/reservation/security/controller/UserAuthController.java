package com.complejolapasionaria.reservation.security.controller;

import com.complejolapasionaria.reservation.dto.AuthenticationRequestUserDto;
import com.complejolapasionaria.reservation.dto.AuthenticationResponseDto;
import com.complejolapasionaria.reservation.dto.RequestUserDto;
import com.complejolapasionaria.reservation.dto.ResponseUserDto;
import com.complejolapasionaria.reservation.security.service.IAuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Register and Login to use the app")
public class UserAuthController {

    private final IAuthenticationService iAuthenticationService;
    public UserAuthController(IAuthenticationService iAuthenticationService) {
        this.iAuthenticationService = iAuthenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseUserDto> signUp(@Valid @RequestBody RequestUserDto dto) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(iAuthenticationService.saveUser(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> signIn(@Valid @RequestBody AuthenticationRequestUserDto dto) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iAuthenticationService.logIn(dto));
    }
}

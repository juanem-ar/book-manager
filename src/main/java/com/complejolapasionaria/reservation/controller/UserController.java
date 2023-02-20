package com.complejolapasionaria.reservation.controller;

import com.complejolapasionaria.reservation.dto.UserResponseDto;
import com.complejolapasionaria.reservation.service.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Tag(name ="Users Controller", description = "Edit, Delete and Get info to user")
public class UserController {
    private final IUserService iUserService;

    public UserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @GetMapping
    @Secured(value = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<UserResponseDto> getUserInfo(Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(iUserService.getUserById(authentication));
    }
    void deleteUser(){}
    void editUserInfo(){}
}

package com.reservation.manager.controller;

import com.reservation.manager.dto.RequestPatchUserDto;
import com.reservation.manager.dto.UserResponseDto;
import com.reservation.manager.service.IUserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name ="Users Controller", description = "Edit, Delete and Get info to user")
@RequiredArgsConstructor
@SecurityRequirement(name="Bearer Authentication")
public class UserController {
    private final IUserService iUserService;

    @GetMapping
    @Secured(value = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<UserResponseDto> getUserInfo(Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(iUserService.getUserById(authentication));
    }
    @DeleteMapping
    @Secured(value = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<String> deleteUser(Authentication authentication){
        return ResponseEntity.status(HttpStatus.OK).body(iUserService.removeUserByAuth(authentication));
    }
    @PatchMapping
    @Secured(value = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<UserResponseDto> editUserInfo(@Validated @RequestBody RequestPatchUserDto dto, Authentication authentication){
        return ResponseEntity.status(HttpStatus.OK).body(iUserService.updateUser(dto,authentication));
    }
}

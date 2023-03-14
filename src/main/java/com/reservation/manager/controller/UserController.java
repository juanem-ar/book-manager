package com.reservation.manager.controller;

import com.reservation.manager.dto.RequestPatchUserDto;
import com.reservation.manager.dto.UserResponseDto;
import com.reservation.manager.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(method = "GET", summary = "User info detail", description = "Get all user-authenticated info",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(
                            schema = @Schema(hidden = true)))
            }
    )
    @GetMapping
    @Secured(value = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<UserResponseDto> getUserInfo(Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(iUserService.getUserById(authentication));
    }

    @Operation(method = "DELETE", summary = "Delete user", description = "Remove user authenticated and return to login (\"/auth/login\")",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(
                            mediaType = "application/json", schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(
                            schema = @Schema(hidden = true)))
            }
    )
    @DeleteMapping
    @Secured(value = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<String> deleteUser(Authentication authentication){
        return ResponseEntity.status(HttpStatus.OK).body(iUserService.removeUserByAuth(authentication));
    }

    @Operation(method = "PATCH", summary = "Edit user", description = "It's can edit: first and lastname, email, date of birth, address, area code and phone number",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(
                            mediaType = "application/json", schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(
                            schema = @Schema(hidden = true)))
            }
    )
    @PatchMapping
    @Secured(value = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<UserResponseDto> editUserInfo(@Validated @RequestBody RequestPatchUserDto dto, Authentication authentication){
        return ResponseEntity.status(HttpStatus.OK).body(iUserService.updateUser(dto,authentication));
    }
}

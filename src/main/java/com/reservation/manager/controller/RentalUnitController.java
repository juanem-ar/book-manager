package com.reservation.manager.controller;

import com.reservation.manager.dto.*;
import com.reservation.manager.dto.page.RentalUnitPageDto;
import com.reservation.manager.service.IRentalUnitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rental-units")
@SecurityRequirement(name="Bearer Authentication")
@RequiredArgsConstructor
public class RentalUnitController {

    private final IRentalUnitService iRentalUnitService;

    @Operation(method = "POST", summary = "Create a rental unit", description = "Create a rental unit by admin.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "OK", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = RentalUnitResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(
                            schema = @Schema(hidden = true)))
            }
    )
    @PostMapping("/save")
    @Secured(value = {"ROLE_ADMIN"})
    public ResponseEntity<RentalUnitResponseDto> saveRentalUnit(@Validated @RequestBody
                                                                RentalUnitRequestDto dto,
                                                                Authentication authentication) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(iRentalUnitService.save(dto, authentication));
    }

    @Operation(method = "GET", summary = "Get a rental unit", description = "Get all rental unit details",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = RentalUnitResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(
                            schema = @Schema(hidden = true)))
            }
    )
    @GetMapping("/{id}")
    @Secured(value = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<RentalUnitResponseDto> getRentalUnit(@PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iRentalUnitService.getRentalUnitById(id));
    }

    @Operation(method = "GET", summary = "Get reservations by unit", description = "Get all reservations by rental unit. Return dto with pagination and sorted.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = RentalUnitAdminResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(
                            schema = @Schema(hidden = true)))
            }
    )
    @GetMapping("/{id}/reservations")
    @Secured(value = {"ROLE_ADMIN"})
    public ResponseEntity<RentalUnitAdminResponseDto> getRentalUnitByAdmin(@RequestParam(value = "page", defaultValue = "1") int page, HttpServletRequest httpServletRequest, @PathVariable Long id, Authentication authentication) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iRentalUnitService.getRentalUnitByAdmin(page, id, authentication, httpServletRequest));
    }

    @Operation(method = "GET", summary = "Get all rental units", description = "Get all rental units. Return dto with pagination and sorted.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = RentalUnitPageDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(
                            schema = @Schema(hidden = true)))
            }
    )
    @GetMapping
    @Secured(value = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<RentalUnitPageDto> getAllRentalUnit(@RequestParam(value = "page", defaultValue = "1") int page, HttpServletRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iRentalUnitService.getAllRentalUnit(page,request));
    }

    @Operation(method = "PATCH", summary = "Edit rental unit as admin", description = "An admin can edit his rental units. It's can edit name, description, number of guests, bedrooms or rooms and finally it's can edit pool type (PRIVATE or PUBLIC/ SHARED)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = RentalUnitResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(
                            schema = @Schema(hidden = true)))
            }
    )
    @PatchMapping("/{id}")
    @Secured(value = {"ROLE_ADMIN"})
    public ResponseEntity<RentalUnitResponseDto> updateRentalUnit(@Validated @PathVariable Long id,
                                                                  @RequestBody RentalUnitPatchRequestDto dto,
                                                                  Authentication authentication) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iRentalUnitService.updateRentalUnit(id, dto ,authentication));
    }

    @Operation(method = "DELETE", summary = "Edit rental unit as admin", description = "An admin can edit his rental units. It's can edit name, description, number of guests, bedrooms or rooms and finally it's can edit pool type (PRIVATE or PUBLIC/ SHARED)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(
                            mediaType = "application/json", schema = @Schema(description = "Rental unit id:  #ID. Removed by admin: #ADMIN_FULLNAME"))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(
                            schema = @Schema(hidden = true)))
            }
    )
    @DeleteMapping("/{id}")
    @Secured(value = {"ROLE_ADMIN"})
    public String removeRentalUnit(@PathVariable Long id,Authentication authentication) throws Exception {
        return iRentalUnitService.removeRentalUnit(id,authentication);
    }

    @Operation(method = "PATCH", summary = "Lock rental unit as admin", description = "An admin can edit his rental units.  ",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(
                            mediaType = "application/json", schema = @Schema(description = "Rental unit id:  #ID. Locked by admin: #ADMIN_FULLNAME"))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(
                            schema = @Schema(hidden = true)))
            }
    )
    @PatchMapping("/{id}/lock")
    @Secured(value = {"ROLE_ADMIN"})
    public ResponseEntity<String> lockRentalUnitByAdmin(@PathVariable Long id, Authentication authentication) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(iRentalUnitService.lockRentalUnit(id, authentication));
    }

    @Operation(method = "PATCH", summary = "Enable rental unit as admin", description = "An admin can edit his rental units.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(
                            mediaType = "application/json", schema = @Schema(description = "Rental unit id:  #ID. Enable by admin: #ADMIN_FULLNAME"))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(
                            schema = @Schema(hidden = true)))
            }
    )
    @PatchMapping("/{id}/enable")
    @Secured(value = {"ROLE_ADMIN"})
    public ResponseEntity<String> enableRentalUnitByAdmin(@PathVariable Long id, Authentication authentication) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(iRentalUnitService.enableRentalUnit(id, authentication));
    }
}

package com.reservation.manager.controller;

import com.reservation.manager.dto.CommerceBuildingRequestDto;
import com.reservation.manager.dto.CommerceBuildingResponseDto;
import com.reservation.manager.dto.page.CommerceBuildingPageDto;
import com.reservation.manager.service.ICommerceBuildingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/commerce-buildings")
@SecurityRequirement(name="Bearer Authentication")
@Tag(name ="Commerce Buildings Controller", description = "Edit, Delete and Get info to commerce")
@RequiredArgsConstructor
public class CommerceBuildingController {

    private final ICommerceBuildingService iCommerceBuildingService;

    @Operation(method = "POST", summary = "Create commerce as admin", description = "An admin can create a commerce with a name, a phone number and an address.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "OK", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = CommerceBuildingResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(
                            schema = @Schema(hidden = true)))
            }
    )
    @PostMapping("/save")
    @Secured(value = {"ROLE_ADMIN"})
    public ResponseEntity<CommerceBuildingResponseDto> saveCommerceBuilding(@Validated @RequestBody CommerceBuildingRequestDto dto, Authentication authentication) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(iCommerceBuildingService.save(dto,authentication));
    }

    @Operation(method = "GET", summary = "Get commerce details by id", description = "Get all commerce details by commerce id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = CommerceBuildingResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(
                            schema = @Schema(hidden = true)))
            }
    )
    @GetMapping("/{id}")
    @Secured(value = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<CommerceBuildingResponseDto> getCommerceBuilding(@PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iCommerceBuildingService.getCommerceBuildingById(id));
    }

    @Operation(method = "GET", summary = "Get all commerces", description = "Get all commerce and commerce details. Return a pagination.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = CommerceBuildingPageDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(
                            schema = @Schema(hidden = true)))
            }
    )
    @GetMapping
    @Secured(value = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<CommerceBuildingPageDto> getAllCommerceBuildingsByUserLogged(@RequestParam(value = "page", defaultValue = "1") @PathVariable int page, HttpServletRequest httpServletRequest) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iCommerceBuildingService.getAllCommerceBuildings(page,httpServletRequest));
    }

    @Operation(method = "PATCH", summary = "Edit commerce as admin", description = "An admin can edit his commerce properties. He can edit name, phone and address.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = CommerceBuildingResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(
                            schema = @Schema(hidden = true)))
            }
    )
    @PatchMapping("/{id}")
    @Secured(value = {"ROLE_ADMIN"})
    public ResponseEntity<CommerceBuildingResponseDto> updateCommerceBuilding(@PathVariable Long id,
                                                       @Validated @RequestBody CommerceBuildingRequestDto dto,
                                                       Authentication authentication) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iCommerceBuildingService.updateCommerceBuilding(id,authentication,dto));
    }

    @Operation(method = "DELETE", summary = "Delete commerce as admin", description = "An admin can delete his commerces.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(
                            mediaType = "application/json", schema = @Schema(description = "Commerce Building removed."))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(
                            schema = @Schema(hidden = true)))
            }
    )
    @DeleteMapping("/{id}")
    @Secured(value = {"ROLE_ADMIN"})
    public String removeCommerceBuilding(@PathVariable Long id,Authentication authentication) throws Exception {
        return iCommerceBuildingService.removeCommerceBuilding(id,authentication);
    }

}

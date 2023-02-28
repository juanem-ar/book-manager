package com.complejolapasionaria.reservation.controller;

import com.complejolapasionaria.reservation.dto.CommerceBuildingRequestDto;
import com.complejolapasionaria.reservation.dto.CommerceBuildingResponseDto;
import com.complejolapasionaria.reservation.dto.page.CommerceBuildingPageDto;
import com.complejolapasionaria.reservation.service.ICommerceBuildingService;
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
@RequestMapping("/commerce-buildings")
@SecurityRequirement(name="Bearer Authentication")
@RequiredArgsConstructor
public class CommerceBuildingController {

    private final ICommerceBuildingService iCommerceBuildingService;

    @PostMapping("/save")
    @Secured(value = {"ROLE_ADMIN"})
    public ResponseEntity<CommerceBuildingResponseDto> saveCommerceBuilding(@Validated @RequestBody CommerceBuildingRequestDto dto, Authentication authentication) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(iCommerceBuildingService.save(dto,authentication));
    }

    @GetMapping("/{id}")
    @Secured(value = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<CommerceBuildingResponseDto> getCommerceBuilding(@PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iCommerceBuildingService.getCommerceBuildingById(id));
    }

    @GetMapping
    @Secured(value = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<CommerceBuildingPageDto> getAllCommerceBuildingsByUserLogged(@RequestParam(value = "page", defaultValue = "1") @PathVariable int page, HttpServletRequest httpServletRequest) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iCommerceBuildingService.getAllCommerceBuildings(page,httpServletRequest));
    }

    @PatchMapping("/{id}")
    @Secured(value = {"ROLE_ADMIN"})
    public ResponseEntity<CommerceBuildingResponseDto> updateCommerceBuilding(@PathVariable Long id,
                                                       @Validated @RequestBody CommerceBuildingRequestDto dto,
                                                       Authentication authentication) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iCommerceBuildingService.updateCommerceBuilding(id,authentication,dto));
    }

    @DeleteMapping("/{id}")
    @Secured(value = {"ROLE_ADMIN"})
    public String removeCommerceBuilding(@PathVariable Long id,Authentication authentication) throws Exception {
        return iCommerceBuildingService.removeCommerceBuilding(id,authentication);
    }

}

package com.complejolapasionaria.reservation.controller;

import com.complejolapasionaria.reservation.dto.RentalUnitAdminResponseDto;
import com.complejolapasionaria.reservation.dto.RentalUnitPatchRequestDto;
import com.complejolapasionaria.reservation.dto.RentalUnitRequestDto;
import com.complejolapasionaria.reservation.dto.RentalUnitResponseDto;
import com.complejolapasionaria.reservation.dto.page.RentalUnitPageDto;
import com.complejolapasionaria.reservation.service.IRentalUnitService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rental-units")
@SecurityRequirement(name="Bearer Authentication")
public class RentalUnitController {

    private final IRentalUnitService iRentalUnitService;

    public RentalUnitController(IRentalUnitService iRentalUnitService) {
        this.iRentalUnitService = iRentalUnitService;
    }

    @PostMapping("/save")
    @Secured(value = {"ROLE_ADMIN"})
    public ResponseEntity<RentalUnitResponseDto> saveRentalUnit(@Validated @RequestBody
                                                                RentalUnitRequestDto dto,
                                                                Authentication authentication) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(iRentalUnitService.save(dto, authentication));
    }

    @GetMapping("/{id}")
    @Secured(value = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<RentalUnitResponseDto> getRentalUnit(@PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iRentalUnitService.getRentalUnitById(id));
    }
    @GetMapping("/{id}/reservations")
    @Secured(value = {"ROLE_ADMIN"})
    public ResponseEntity<RentalUnitAdminResponseDto> getRentalUnitByAdmin(@RequestParam(value = "page", defaultValue = "1") int page, HttpServletRequest httpServletRequest, @PathVariable Long id, Authentication authentication) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iRentalUnitService.getRentalUnitByAdmin(page, id, authentication, httpServletRequest));
    }

    @GetMapping
    @Secured(value = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<RentalUnitPageDto> getAllRentalUnit(@RequestParam(value = "page", defaultValue = "1") int page, HttpServletRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iRentalUnitService.getAllRentalUnit(page,request));
    }
    @PatchMapping("/{id}")
    @Secured(value = {"ROLE_ADMIN"})
    public ResponseEntity<RentalUnitResponseDto> updateRentalUnit(@Validated @PathVariable Long id,
                                                                  @RequestBody RentalUnitPatchRequestDto dto,
                                                                  Authentication authentication) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iRentalUnitService.updateRentalUnit(id, dto ,authentication));
    }

    @DeleteMapping("/{id}")
    @Secured(value = {"ROLE_ADMIN"})
    public String removeRentalUnit(@PathVariable Long id,Authentication authentication) throws Exception {
        return iRentalUnitService.removeRentalUnit(id,authentication);
    }
    @PostMapping("/{id}/lock")
    @Secured(value = {"ROLE_ADMIN"})
    public ResponseEntity<String> lockRentalUnitByAdmin(@PathVariable Long id, Authentication authentication) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(iRentalUnitService.lockRentalUnit(id, authentication));
    }
    @PostMapping("/{id}/enable")
    @Secured(value = {"ROLE_ADMIN"})
    public ResponseEntity<String> enableRentalUnitByAdmin(@PathVariable Long id, Authentication authentication) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(iRentalUnitService.enableRentalUnit(id, authentication));
    }
}

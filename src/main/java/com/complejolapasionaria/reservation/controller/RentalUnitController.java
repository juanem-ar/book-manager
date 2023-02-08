package com.complejolapasionaria.reservation.controller;

import com.complejolapasionaria.reservation.dto.RentalUnitRequestDto;
import com.complejolapasionaria.reservation.dto.RentalUnitResponseDto;
import com.complejolapasionaria.reservation.service.IRentalUnitService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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

    @GetMapping("/all/{id}")
    public ResponseEntity<List<Void>> getAllRentalUnit(@PathVariable Long id){
        return null;
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateRentalUnit(@PathVariable Long id){
        return null;
    }

    @DeleteMapping("/{id}")
    public void removeRentalUnit(@PathVariable Long id){}
}

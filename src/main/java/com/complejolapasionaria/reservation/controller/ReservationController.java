package com.complejolapasionaria.reservation.controller;

import com.complejolapasionaria.reservation.dto.ReservationRequestDto;
import com.complejolapasionaria.reservation.dto.ReservationResponseDto;
import com.complejolapasionaria.reservation.service.IReservationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
@SecurityRequirement(name="Bearer Authentication")
public class ReservationController {

    private final IReservationService iReservationService;

    public ReservationController(IReservationService iReservationService) {
        this.iReservationService = iReservationService;
    }

    @PostMapping("/create/rental-units/{id}")
    @Secured(value = {"ROLE_USER"})
    public ResponseEntity<ReservationResponseDto> reservationCreateByUser(
            @Validated @RequestBody ReservationRequestDto dto,
            @PathVariable Long id,
            Authentication authentication) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(iReservationService.userReserve(dto, authentication, id));
    }

    @PostMapping("/create/rental-units/{id}/users/{userId}")
    @Secured(value = {"ROLE_ADMIN"})
    public ResponseEntity<ReservationResponseDto> reservationCreateByAdmin(
            @Validated @RequestBody ReservationRequestDto dto,
            Authentication authentication,
            @PathVariable Long id,
            @PathVariable Long userId) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(iReservationService.adminReserve(dto,authentication, userId, id));
    }

    @GetMapping("/{id}")
    @Secured(value = {"ROLE_USER"})
    public ResponseEntity<ReservationResponseDto> getReservationById(@PathVariable Long id, Authentication authentication)throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(iReservationService.getById(id, authentication));
    }

    @GetMapping("/{id}/any")
    @Secured(value = {"ROLE_ADMIN"})
    public ResponseEntity<ReservationResponseDto> getReservationByIdAndAdmin(@PathVariable Long id, Authentication authentication)throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(iReservationService.getByIdAndAdminRole(id, authentication));
    }
    @PatchMapping("/{id}/users/{userId}")
    @Secured(value = {"ROLE_ADMIN"})
    public ResponseEntity<ReservationResponseDto> updateReservationByAdmin(@Validated @RequestBody ReservationRequestDto dto,
                                                                    @PathVariable Long id,
                                                                    @PathVariable Long userId,
                                                                    Authentication authentication) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(iReservationService.update(dto, id, userId, authentication));
    }
    @DeleteMapping("/{id}")
    @Secured(value = {"ROLE_ADMIN"})
    public ResponseEntity<String> removeReservationByAdmin(@PathVariable Long id, Authentication authentication) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(iReservationService.removeReservation(id, authentication));
    }
    @PostMapping("/{id}/confirm")
    @Secured(value = {"ROLE_ADMIN"})
    public ResponseEntity<String> confirmReservationByAdmin(@PathVariable Long id, Authentication authentication) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(iReservationService.confirmReservation(id, authentication));
    }
}

package com.reservation.manager.controller;

import com.reservation.manager.dto.ReservationRequestDto;
import com.reservation.manager.dto.ReservationResponseDto;
import com.reservation.manager.service.IReservationService;
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
@RequestMapping("/reservations")
@SecurityRequirement(name="Bearer Authentication")
@Tag(name ="Reservations Controller", description = "All actions for the reservations.")
@RequiredArgsConstructor
public class ReservationController {

    private final IReservationService iReservationService;

    @Operation(method = "POST", summary = "Create reservation as user", description = "An user can create a reservation",
            responses = {
                    @ApiResponse(responseCode = "201", description = "OK", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = ReservationResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(
                            schema = @Schema(hidden = true)))
            }
    )
    @PostMapping("/create/rental-units/{id}")
    @Secured(value = {"ROLE_USER"})
    public ResponseEntity<ReservationResponseDto> reservationCreateByUser(
            @Validated @RequestBody ReservationRequestDto dto,
            @PathVariable Long id,
            Authentication authentication) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(iReservationService.userReserve(dto, authentication, id));
    }

    @Operation(method = "POST", summary = "Create reservation as admin", description = "An admin can create a reservation",
            responses = {
                    @ApiResponse(responseCode = "201", description = "OK", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = ReservationResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(
                            schema = @Schema(hidden = true)))
            }
    )
    @PostMapping("/create/rental-units/{id}/users/{userId}")
    @Secured(value = {"ROLE_ADMIN"})
    public ResponseEntity<ReservationResponseDto> reservationCreateByAdmin(
            @Validated @RequestBody ReservationRequestDto dto,
            Authentication authentication,
            @PathVariable Long id,
            @PathVariable Long userId) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(iReservationService.adminReserve(dto,authentication, userId, id));
    }

    @Operation(method = "GET", summary = "Get reservation", description = "Get all reservations details. If the reservations doesn't belong to user or admin, throw errors",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = ReservationResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(
                            schema = @Schema(hidden = true)))
            }
    )
    @GetMapping("/{id}")
    @Secured(value = {"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<ReservationResponseDto> getReservationById(@PathVariable Long id, Authentication authentication)throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(iReservationService.getById(id, authentication));
    }

    @Operation(method = "PATCH", summary = "Edit reservation", description = "Edit reservation as admin. If the reservations doesn't belong to admin, throw errors. The admin can edit: amount of people, check in date, check out date, cost per night or percent of payment. If this reservation is updated its status changes by IN_PROCESS.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = ReservationResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(
                            schema = @Schema(hidden = true)))
            }
    )
    @PatchMapping("/{id}/users/{userId}")
    @Secured(value = {"ROLE_ADMIN"})
    public ResponseEntity<ReservationResponseDto> updateReservationByAdmin(@Validated @RequestBody ReservationRequestDto dto,
                                                                    @PathVariable Long id,
                                                                    @PathVariable Long userId,
                                                                    Authentication authentication) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(iReservationService.update(dto, id, userId, authentication));
    }

    @Operation(method = "DELETE", summary = "Delete reservation", description = "Delete reservation as admin. If the reservations doesn't belong to admin, throw errors.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(
                            mediaType = "application/json", schema = @Schema(description = "Reservation id: #ID removed by admin: #FULLNAMEADMIN"))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(
                            schema = @Schema(hidden = true)))
            }
    )
    @DeleteMapping("/{id}")
    @Secured(value = {"ROLE_ADMIN"})
    public ResponseEntity<String> removeReservationByAdmin(@PathVariable Long id, Authentication authentication) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(iReservationService.removeReservation(id, authentication));
    }

    @Operation(method = "PATCH", summary = "Edit reservation status", description = "Method for confirm the reservation as admin.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(
                            mediaType = "application/json", schema = @Schema(description = "Reservation id: #ID accepted by admin: #FULLNAMEADMIN"))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(
                            schema = @Schema(hidden = true)))
            }
    )
    @PatchMapping("/confirm/{id}")
    @Secured(value = {"ROLE_ADMIN"})
    public ResponseEntity<String> confirmReservationByAdmin(@PathVariable Long id, Authentication authentication) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(iReservationService.confirmReservation(id, authentication));
    }

    @Operation(hidden = true)
    @PatchMapping("/confirm-mp-payment/{id}")
    public ResponseEntity<String> confirmReservationByMp(@PathVariable Long id,
                                                         @RequestParam(required = false) String collection_id,
                                                         @RequestParam(required = false) String collection_status,
                                                         @RequestParam(required = false) String payment_id,
                                                         @RequestParam(required = false) String status,
                                                         @RequestParam(required = false) String external_reference,
                                                         @RequestParam(required = false) String payment_type,
                                                         @RequestParam(required = false) String merchant_order_id,
                                                         @RequestParam(required = false) String preference_id,
                                                         @RequestParam(required = false) String site_id,
                                                         @RequestParam(required = false) String processing_mode,
                                                         @RequestParam(required = false) String merchant_account_id) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(iReservationService.confirmReservation(id, collection_status, status));
    }
}

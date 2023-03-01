package com.reservation.manager.dto;

import com.reservation.manager.Enum.EPool;
import com.reservation.manager.Enum.EStatus;
import com.reservation.manager.dto.page.ReservationPageDto;
import lombok.Data;

@Data
public class RentalUnitAdminResponseDto {
    private String buildingName;
    private String name;
    private String description;
    private int maximumAmountOfGuests;
    private int numberOfBedrooms;
    private int numberOfRooms;
    private EStatus status;
    private EPool pool;
    //private List<ReservationResponseDto> reservationList;
    private ReservationPageDto reservationPageDto;
}

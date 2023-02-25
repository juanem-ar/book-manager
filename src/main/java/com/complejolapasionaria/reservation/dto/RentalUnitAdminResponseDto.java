package com.complejolapasionaria.reservation.dto;

import com.complejolapasionaria.reservation.Enum.EPool;
import com.complejolapasionaria.reservation.Enum.EStatus;
import com.complejolapasionaria.reservation.dto.page.ReservationPageDto;
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

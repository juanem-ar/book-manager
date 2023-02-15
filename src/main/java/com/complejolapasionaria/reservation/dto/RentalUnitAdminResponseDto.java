package com.complejolapasionaria.reservation.dto;

import com.complejolapasionaria.reservation.Enum.EPool;
import com.complejolapasionaria.reservation.Enum.EStatus;
import com.complejolapasionaria.reservation.model.Reservation;
import lombok.Data;

import java.util.List;

@Data
public class RentalUnitAdminResponseDto {
    private Long buildingId;
    private String name;
    private String description;
    private int maximumAmountOfGuests;
    private int numberOfBedrooms;
    private int numberOfRooms;
    private EStatus status;
    private EPool pool;
    private List<Reservation> reservationList;
}

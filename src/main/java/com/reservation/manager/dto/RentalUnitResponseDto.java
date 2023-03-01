package com.reservation.manager.dto;

import com.reservation.manager.Enum.EPool;
import com.reservation.manager.Enum.EStatus;
import lombok.Data;

@Data
public class RentalUnitResponseDto {
    private Long buildingId;
    private String name;
    private String description;
    private int maximumAmountOfGuests;
    private int numberOfBedrooms;
    private int numberOfRooms;
    private EStatus status;
    private EPool pool;
}

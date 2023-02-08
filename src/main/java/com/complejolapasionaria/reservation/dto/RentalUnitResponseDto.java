package com.complejolapasionaria.reservation.dto;

import com.complejolapasionaria.reservation.Enum.EPool;
import com.complejolapasionaria.reservation.Enum.EStatus;
import com.complejolapasionaria.reservation.model.CommerceBuilding;
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

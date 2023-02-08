package com.complejolapasionaria.reservation.dto;

import com.complejolapasionaria.reservation.Enum.EPool;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RentalUnitRequestDto {

    @NotNull(message = "Commerce building name is required")
    private Long buildingId;

    @Size(min = 3, max = 100)
    @NotNull(message = "Unit name is required.")
    private String name;

    @NotNull(message = "Unit description is required")
    @Size(max = 255)
    private String description;

    @NotNull(message = "Maximum of guests are required")
    @Min(value= 1 , message = "Minimum required is 1")
    private int maximumAmountOfGuests;

    @NotNull(message = "Number of bedrooms are required")
    @Min(value= 1 , message = "Minimum required is 1")
    private int numberOfBedrooms;

    @NotNull(message = "Number of rooms are required")
    @Min(value= 1 , message = "Minimum required is 1")
    private int numberOfRooms;

    @NotNull(message = "Pool description is required")
    @Size(min = 6, max = 10)
    private String pool;
}

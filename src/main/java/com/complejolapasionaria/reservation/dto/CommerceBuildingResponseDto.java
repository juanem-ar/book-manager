package com.complejolapasionaria.reservation.dto;

import com.complejolapasionaria.reservation.model.RentalUnit;
import com.complejolapasionaria.reservation.model.User;
import lombok.Data;
import java.util.List;

@Data
public class CommerceBuildingResponseDto {
    private Long id;
    private String name;
    private String address;
    private List<RentalUnit> rentalUnitList;
    private User owner;
}

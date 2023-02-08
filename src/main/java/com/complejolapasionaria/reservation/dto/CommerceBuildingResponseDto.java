package com.complejolapasionaria.reservation.dto;

import com.complejolapasionaria.reservation.model.RentalUnit;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class CommerceBuildingResponseDto {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private List<RentalUnit> rentalUnitList;
    private Map<String,String> ownerDetails;
}

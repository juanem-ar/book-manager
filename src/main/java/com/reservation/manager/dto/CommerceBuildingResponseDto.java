package com.reservation.manager.dto;

import com.reservation.manager.model.RentalUnit;
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

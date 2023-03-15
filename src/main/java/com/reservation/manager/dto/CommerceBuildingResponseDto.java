package com.reservation.manager.dto;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class CommerceBuildingResponseDto {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private List<RentalUnitResponseDto> rentalUnitList;
    private Map<String,String> ownerDetails;
}

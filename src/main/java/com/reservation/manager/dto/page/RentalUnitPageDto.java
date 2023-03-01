package com.reservation.manager.dto.page;

import com.reservation.manager.dto.RentalUnitResponseDto;
import lombok.Data;

import java.util.List;

@Data
public class RentalUnitPageDto {
    private String nextPage;
    private String previousPage;
    private int totalPages;
    private List<RentalUnitResponseDto> rentalUnitDtoList;
}

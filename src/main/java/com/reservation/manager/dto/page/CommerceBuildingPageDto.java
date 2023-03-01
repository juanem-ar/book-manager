package com.reservation.manager.dto.page;

import com.reservation.manager.dto.CommerceBuildingResponseDto;
import lombok.Data;

import java.util.List;

@Data
public class CommerceBuildingPageDto {
    private String nextPage;
    private String previousPage;
    private int totalPages;
    private List<CommerceBuildingResponseDto> commerceBuildingDtoList;
}

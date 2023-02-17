package com.complejolapasionaria.reservation.dto.page;

import com.complejolapasionaria.reservation.dto.ReservationResponseDto;
import lombok.Data;
import java.util.List;

@Data
public class ReservationPageDto {
    private String nextPage;
    private String previousPage;
    private int totalPages;
    private List<ReservationResponseDto> reservationDtoList;
}

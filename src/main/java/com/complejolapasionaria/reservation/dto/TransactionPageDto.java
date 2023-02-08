package com.complejolapasionaria.reservation.dto;

import lombok.Data;

import java.util.List;

@Data
public class TransactionPageDto {
    String nextPage;
    String previousPage;
    int totalPages;
    List<CommerceBuildingResponseDto> transactionDtoList;
}

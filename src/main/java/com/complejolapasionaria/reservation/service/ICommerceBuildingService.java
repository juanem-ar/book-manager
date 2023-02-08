package com.complejolapasionaria.reservation.service;

import com.complejolapasionaria.reservation.dto.CommerceBuildingRequestDto;
import com.complejolapasionaria.reservation.dto.CommerceBuildingResponseDto;
import com.complejolapasionaria.reservation.dto.TransactionPageDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

public interface ICommerceBuildingService {
    CommerceBuildingResponseDto save(CommerceBuildingRequestDto dto, Authentication authentication) throws Exception;
    CommerceBuildingResponseDto getCommerceBuildingById(Long id) throws Exception;
    TransactionPageDto getAllCommerceBuildingsByUserLogged(int page, Authentication authentication, HttpServletRequest httpServletRequest) throws Exception;
}

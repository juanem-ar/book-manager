package com.complejolapasionaria.reservation.service;

import com.complejolapasionaria.reservation.dto.CommerceBuildingRequestDto;
import com.complejolapasionaria.reservation.dto.CommerceBuildingResponseDto;
import org.springframework.security.core.Authentication;

public interface ICommerceBuildingService {
    CommerceBuildingResponseDto save(CommerceBuildingRequestDto dto, Authentication authentication) throws Exception;
}

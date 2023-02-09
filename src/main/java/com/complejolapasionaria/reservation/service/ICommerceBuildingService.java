package com.complejolapasionaria.reservation.service;

import com.complejolapasionaria.reservation.dto.CommerceBuildingRequestDto;
import com.complejolapasionaria.reservation.dto.CommerceBuildingResponseDto;
import com.complejolapasionaria.reservation.dto.page.CommerceBuildingPageDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface ICommerceBuildingService {
    CommerceBuildingResponseDto save(CommerceBuildingRequestDto dto, Authentication authentication) throws Exception;
    CommerceBuildingResponseDto getCommerceBuildingById(Long id) throws Exception;
    CommerceBuildingPageDto getAllCommerceBuildings(int page , HttpServletRequest httpServletRequest) throws Exception;
    CommerceBuildingResponseDto updateCommerceBuilding(Long id, Authentication authentication, CommerceBuildingRequestDto dto) throws Exception;
    String removeCommerceBuilding(Long id, Authentication authentication) throws Exception;
}

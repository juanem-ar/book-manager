package com.complejolapasionaria.reservation.service;

import com.complejolapasionaria.reservation.dto.RentalUnitPatchRequestDto;
import com.complejolapasionaria.reservation.dto.RentalUnitRequestDto;
import com.complejolapasionaria.reservation.dto.RentalUnitResponseDto;
import com.complejolapasionaria.reservation.dto.page.RentalUnitPageDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface IRentalUnitService {
    RentalUnitResponseDto save(RentalUnitRequestDto dto, Authentication authentication) throws Exception;
    RentalUnitResponseDto getRentalUnitById(Long id) throws Exception;
    RentalUnitPageDto getAllRentalUnit(int page, HttpServletRequest request) throws Exception;
    RentalUnitResponseDto updateRentalUnit(Long id, RentalUnitPatchRequestDto dto, Authentication authentication) throws Exception;

}

package com.reservation.manager.service;

import com.reservation.manager.dto.RentalUnitAdminResponseDto;
import com.reservation.manager.dto.RentalUnitPatchRequestDto;
import com.reservation.manager.dto.RentalUnitRequestDto;
import com.reservation.manager.dto.RentalUnitResponseDto;
import com.reservation.manager.dto.page.RentalUnitPageDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface IRentalUnitService {
    RentalUnitResponseDto save(RentalUnitRequestDto dto, Authentication authentication) throws Exception;
    RentalUnitResponseDto getRentalUnitById(Long id) throws Exception;
    RentalUnitPageDto getAllRentalUnit(int page, HttpServletRequest request) throws Exception;
    RentalUnitResponseDto updateRentalUnit(Long id, RentalUnitPatchRequestDto dto, Authentication authentication) throws Exception;
    String removeRentalUnit(Long id,Authentication authentication) throws Exception;
    RentalUnitAdminResponseDto getRentalUnitByAdmin(int page, Long id, Authentication authentication, HttpServletRequest httpServletRequest) throws Exception;
    String lockRentalUnit(Long id, Authentication authentication) throws Exception;
    String enableRentalUnit(Long id, Authentication authentication) throws Exception;
}

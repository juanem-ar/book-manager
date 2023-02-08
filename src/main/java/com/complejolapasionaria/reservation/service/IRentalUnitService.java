package com.complejolapasionaria.reservation.service;

import com.complejolapasionaria.reservation.dto.RentalUnitRequestDto;
import com.complejolapasionaria.reservation.dto.RentalUnitResponseDto;
import org.springframework.security.core.Authentication;

public interface IRentalUnitService {
    RentalUnitResponseDto save(RentalUnitRequestDto dto, Authentication authentication) throws Exception;

}

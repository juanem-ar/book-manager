package com.complejolapasionaria.reservation.service.impl;

import com.complejolapasionaria.reservation.Enum.EStatus;
import com.complejolapasionaria.reservation.dto.ReservationRequestDto;
import com.complejolapasionaria.reservation.dto.ReservationResponseDto;
import com.complejolapasionaria.reservation.mapper.IReservationMapper;
import com.complejolapasionaria.reservation.model.Reservation;
import com.complejolapasionaria.reservation.model.User;
import com.complejolapasionaria.reservation.repository.IReservationRepository;
import com.complejolapasionaria.reservation.repository.IUserRepository;
import com.complejolapasionaria.reservation.service.IReservationService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements IReservationService {
    private final IUserRepository iUserRepository;
    private final IReservationRepository iReservationRepository;
    private final IReservationMapper iReservationMapper;

    public ReservationServiceImpl(IUserRepository iUserRepository, IReservationRepository iReservationRepository, IReservationMapper iReservationMapper) {
        this.iUserRepository = iUserRepository;
        this.iReservationRepository = iReservationRepository;
        this.iReservationMapper = iReservationMapper;
    }

    @Override
    public ReservationResponseDto saveReservation(ReservationRequestDto dto, Authentication authentication) throws Exception {
        User user = iUserRepository.findByEmail(authentication.getName());
        //TODO VALIDAR TODO EL METODO
        Reservation reservation = iReservationMapper.toEntity(dto);
        reservation.setDeleted(Boolean.FALSE);
        reservation.setStatus(EStatus.STATUS_IN_PROCESS);
        reservation.setUser(user);
        Reservation reservationReserved = iReservationRepository.save(reservation);
        return iReservationMapper.toResponseDto(reservationReserved);
    }
}

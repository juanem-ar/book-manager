package com.reservation.manager.dto;

import com.reservation.manager.Enum.EDocumentTypes;
import com.reservation.manager.model.Reservation;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
public class UserResponseDto {
    private Long id;
    private Boolean deleted;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private String address;
    private EDocumentTypes documentType;
    private String documentNumber;
    private String areaCode;
    private String phoneNumber;
    private Timestamp creationDate;
    private Timestamp updateDate;
    private List<Reservation> reservationList;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
}

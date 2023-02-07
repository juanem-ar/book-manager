package com.complejolapasionaria.reservation.dto;

import com.complejolapasionaria.reservation.model.Reservation;
import com.complejolapasionaria.reservation.model.Role;
import lombok.Data;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Data
public class ResponseUserDto {

    private Long id;
    private Boolean deleted;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private String address;
    private String phoneNumber;
    private Timestamp creationDate;
    private Timestamp updateDate;
    private Role role;
    private List<Reservation> reservationList;

}

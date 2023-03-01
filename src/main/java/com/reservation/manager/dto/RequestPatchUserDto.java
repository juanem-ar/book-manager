package com.reservation.manager.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestPatchUserDto {
    @Size(min = 3, max = 50)
    @NotNull(message = "Firstname is required")
    private String firstName;

    @Size(min = 3, max = 50)
    @NotNull(message = "Lastname is required")
    private String lastName;

    @Email
    @Size(min = 6)
    @NotNull(message = "Email is required")
    private String email;

    @NotNull(message = "Date of birth is required (YYYY-MM-dd).")
    @Column(name = "day_of_birth")
    private LocalDate dateOfBirth;

    @NotNull(message = "Address is required")
    private String address;

    @NotNull(message = "Phone number is required")
    @Column(name = "phone_number")
    private String phoneNumber;
}

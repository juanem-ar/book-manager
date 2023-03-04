package com.reservation.manager.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUserDto {

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

    @Length(min = 8)
    @NotNull(message = "Password is required")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$", message = "- at least 8 characters\n" +
            "- must contain at least 1 uppercase letter, 1 lowercase letter, and 1 number\n" +
            "- Can contain special characters")
    private String password;

    @NotNull(message = "Date of birth is required (YYYY-MM-dd).")
    @Column(name = "day_of_birth")
    private LocalDate dateOfBirth;

    @NotNull(message = "Address is required")
    private String address;

    @NotNull(message = "Phone number is required")
    @Column(name = "phone_number")
    @Pattern(regexp = "(\\+54[\\d]{10})|(\\+54[\\d]{11})", message = "Format: +543515168306")
    private String phoneNumber;

    private String role;
}

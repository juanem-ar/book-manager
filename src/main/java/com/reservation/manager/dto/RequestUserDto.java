package com.reservation.manager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "John", description = "User name", minLength = 3, maxLength = 50)
    private String firstName;

    @Size(min = 3, max = 50)
    @NotNull(message = "Lastname is required")
    @Schema(example = "Secada", description = "User lastname", minLength = 3, maxLength = 50)
    private String lastName;

    @Email
    @Size(min = 6)
    @NotNull(message = "Email is required")
    @Schema(format = "email", example = "John@gmail.com", description = "User email", minLength = 6)
    private String email;

    @Length(min = 8)
    @NotNull(message = "Password is required")
    @Pattern(regexp = "((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).{8,20})", message = "- at least 8 characters, " +
            "also must contain at least 1 number, 1 uppercase and 1 lowercase letter")
    private String password;

    @NotNull(message = "Date of birth is required (YYYY-MM-dd).")
    @Column(name = "day_of_birth")
    @Schema(format = "YYYY-MM-dd", example = "1990-09-18", description = "Date of birth")
    private LocalDate dateOfBirth;

    @NotNull(message = "Address is required")
    @Schema(example = "Suarez de Figueroa 1867", description = "User address")
    private String address;

    @NotNull(message = "Document type is required. Choose between \"DNI\",\"CI\",\"LC\",\"LE\",\"OTRO\"")
    @Pattern(regexp = "(^(?=.*[A-Z])|^(?=.*[a-z])).{2,4}$", message = "Choose between \"DNI\",\"CI\",\"LC\",\"LE\",\"OTRO\"")
    @Schema(example = "DNI", description = "User document type")
    private String documentType;

    @NotNull(message = "Document number is required")
    @Pattern(regexp = "\\d{7,8}$", message = "Invalid number")
    private String documentNumber;

    @NotNull(message = "Area code is required")
    @Pattern(regexp = "^[+][1-9]{2,3}$", message = "Format: (+) + Area code + phone number. Without spaces and special characters")
    private String areaCode;

    @NotNull(message = "Phone number is required")
    @Pattern(regexp = "(^[1-9]\\d{8,9})$", message = "Format: Phone number without spaces ")
    private String phoneNumber;

    @NotNull(message = "Role is required")
    @Schema(example = "user", description = "User role")
    private String role;
}

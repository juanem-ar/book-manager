package com.complejolapasionaria.reservation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequestUserDto {

    @NotNull(message = "Email is required")
    @Email(message = "Email format invalid")
    @Size(min = 6)
    @Schema(format = "email", example = "juanem@hotmail.com", minLength = 6, description = "User email")
    private String email;

    @NotNull(message = "Password is required")
    @Length(min = 8, max = 25)
    @Schema(required = true, example = "12345678", description = "Password", minLength = 8, maxLength = 25)
    private String password;
}

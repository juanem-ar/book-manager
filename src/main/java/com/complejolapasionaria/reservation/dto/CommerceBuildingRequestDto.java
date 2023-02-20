package com.complejolapasionaria.reservation.dto;

import com.complejolapasionaria.reservation.model.RentalUnit;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommerceBuildingRequestDto {

    @Size(min = 3, max = 100)
    @NotNull(message = "commerce building's name is required.")
    private String name;

    @Size(min = 3, max = 250)
    @NotNull(message = "commerce building's address is required.")
    private String address;

    private String phoneNumber;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<RentalUnit> rentalUnitList;
}

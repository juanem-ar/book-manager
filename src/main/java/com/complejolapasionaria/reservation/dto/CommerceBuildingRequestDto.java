package com.complejolapasionaria.reservation.dto;

import com.complejolapasionaria.reservation.model.RentalUnit;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

    private List<RentalUnit> rentalUnitList;
}

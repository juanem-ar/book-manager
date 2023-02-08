package com.complejolapasionaria.reservation.model;

import com.complejolapasionaria.reservation.Enum.EPool;
import com.complejolapasionaria.reservation.Enum.EStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Data
@Table(name = "rental_unit")
@SQLDelete(sql = "UPDATE rental_unit SET deleted= true WHERE id=?")
public class RentalUnit implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean deleted = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.EAGER, optional = false )
    @JoinColumn(name = "buildings_id", updatable = false)
    @JsonIgnore
    private CommerceBuilding building;

    @Size(min = 3, max = 100)
    @NotNull(message = "Name is required.")
    private String name;

    @NotNull(message = "Description is required")
    @Size(min = 3, max = 255)
    private String description;

    @NotNull(message = "Maximum of guests are required")
    @Min(value= 1 , message = "Minimum required is 1")
    private int maximumAmountOfGuests;

    @NotNull(message = "Number of bedrooms are required")
    @Min(value= 1 , message = "Minimum required is 1")
    private int numberOfBedrooms;

    @NotNull(message = "Number of rooms are required")
    @Min(value = 1,message = "Minimum required is 1")
    private int numberOfRooms;

    @Enumerated(EnumType.STRING)
    private EStatus status;

    @Enumerated(EnumType.STRING)
    private EPool pool;
}

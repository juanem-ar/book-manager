package com.complejolapasionaria.reservation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "buildings")
@SQLDelete(sql = "UPDATE buildings SET deleted= true WHERE id=?")
public class CommerceBuilding implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean deleted = Boolean.FALSE;

    @Size(min = 3, max = 100)
    @NotNull(message = "commerce building's name is required.")
    private String name;

    @Size(min = 3, max = 250)
    @NotNull(message = "commerce building's address is required.")
    private String address;

    @OneToMany(mappedBy = "building")
    private List<RentalUnit> rentalUnitList = new ArrayList<>();

    public void addRentalUnit(RentalUnit unit){
        this.rentalUnitList.add(unit);
    }

    public void removeRentalUnit(RentalUnit unit){
        this.rentalUnitList.remove(unit);
    }
}

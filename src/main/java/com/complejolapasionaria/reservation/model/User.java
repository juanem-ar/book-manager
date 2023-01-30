package com.complejolapasionaria.reservation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id=?")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean deleted = Boolean.FALSE;

    @Size(min = 3, max = 50)
    @NotNull(message = "Firstname is required")
    private String firstName;

    @Size(min = 3, max = 50)
    @NotNull(message = "Lastname is required")
    private String lastName;

    @Email
    @NotNull(message = "Email is required")
    @Column(unique = true)
    private String email;

    @Length(min = 8)
    @NotNull(message = "Password is required")
    private String password;

    @NotNull(message = "Date of birth is required (YYYY-MM-dd).")
    @Column(name = "day_of_birth")
    @JsonFormat(pattern = "YYYY-MM-dd")
    private LocalDate dateOfBirth;

    @NotNull(message = "Address is required")
    private String address;

    @NotNull(message = "Phone number is required")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "creation_date",
            updatable = false)
    @CreationTimestamp
    private Timestamp creationDate;

    @Column(name = "update_date",
            nullable = false)
    @UpdateTimestamp
    private Timestamp updateDate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "role")
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Reservation> reservationList = new ArrayList<>();
}

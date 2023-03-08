package com.reservation.manager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.reservation.manager.Enum.EDocumentTypes;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id=?")
public class User implements Serializable, UserDetails {

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
    @NotEmpty(message = "Email is required")
    @Column(unique = true)
    private String email;

    @Length(min = 8)
    @NotNull(message = "Password is required")
    private String password;

    @NotNull(message = "Date of birth is required (YYYY-MM-dd).")
    @Column(name = "day_of_birth")
    @JsonFormat(pattern = "YYYY-MM-dd")
    @Past
    private LocalDate dateOfBirth;

    @NotNull(message = "Address is required")
    private String address;

    @NotNull(message = "Area code is required")
    @Column(name = "area_code")
    private String areaCode;

    @NotNull(message = "Phone number is required")
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotNull(message = "Document type is required")
    @Column(name = "document_type")
    @Enumerated(EnumType.STRING)
    private EDocumentTypes documentType;

    @NotNull(message = "Document number is required")
    @Column(name = "document_number")
    private String documentNumber;

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
    private List<Token> tokens;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Reservation> reservationList = new ArrayList<>();
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.getName().toString()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return !deleted;
    }
}

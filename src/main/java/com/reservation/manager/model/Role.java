package com.reservation.manager.model;

import com.reservation.manager.Enum.ERoles;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
@SQLDelete(sql = "UPDATE roles SET deleted = true WHERE id=?")
public class Role implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Name is required")
    @Column(name = "name")
    private ERoles name;

    @Column(name = "description")
    @NotNull(message = "Description is required")
    private String description;

    @CreationTimestamp
    @Column(name = "creation_date")
    private Timestamp creationDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    private Timestamp updateDate;

    private Boolean deleted;
}

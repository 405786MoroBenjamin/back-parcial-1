package ar.edu.utn.frc.tup.lciii.model;

import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CountryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column
    String name;
    @Column
    String code;
    @Column
    Long population;
    @Column
    Double area;
}

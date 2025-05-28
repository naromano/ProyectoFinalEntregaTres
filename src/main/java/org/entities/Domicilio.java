package org.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Domicilio {
    private Long id;
    private String calle;
    private Integer numero;
    private String localidad;
    private String provincia;
    private String pais;
}

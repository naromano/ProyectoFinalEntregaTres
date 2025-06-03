package org.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
public class Persona {
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private Domicilio domicilio;
}

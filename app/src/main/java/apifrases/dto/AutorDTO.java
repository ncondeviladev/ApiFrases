package apifrases.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutorDTO {
    private Long id;
    private String nombre;
    private Integer anioNacimiento;
    private Integer anioFallecimiento;
    private String profesion;
    private int numeroFrases;
}

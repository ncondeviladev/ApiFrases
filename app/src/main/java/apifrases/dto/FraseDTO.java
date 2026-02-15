package apifrases.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FraseDTO {
    private Long id;
    private String texto;
    private LocalDate fechaProgramada;
    private String autor;
    private String categoria;
    private Long visitas;
}

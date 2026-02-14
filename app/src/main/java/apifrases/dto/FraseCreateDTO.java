package apifrases.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FraseCreateDTO {

    @NotBlank(message = "El texto es obligatorio")
    @Size(max = 500, message = "El texto es demasiado largo")
    private String texto;

    private LocalDate fechaProgramada;

    @NotNull(message = "Debes indicar el ID del autor")
    private Long autorId;

    @NotNull(message = "Debes indicar el ID de la categoria")
    private Long categoriaId;
}

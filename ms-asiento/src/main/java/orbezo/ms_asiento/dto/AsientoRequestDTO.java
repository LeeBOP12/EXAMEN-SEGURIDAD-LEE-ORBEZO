package orbezo.ms_asiento.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsientoRequestDTO {
    @NotNull(message = "El ID del bus es obligatorio")
    @Min(value = 1, message = "ID de bus inválido")
    private Long idBus;

    @NotBlank(message = "El número de asiento es obligatorio")
    @Size(max = 5, message = "El número de asiento no puede exceder 5 caracteres")
    private String numAsi;

    @Pattern(regexp = "^[DOR]$", message = "Estado debe ser D (Disponible), O (Ocupado) o R (Reservado)")
    private String estAsi;
}
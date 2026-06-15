package orbezo.ms_destino.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DestinoRequestDTO {
    @NotBlank(message = "La ciudad es obligatoria")
    @Size(max = 100, message = "La ciudad no puede exceder 100 caracteres")
    private String ciuDest;

    @NotNull(message = "El costo es obligatorio")
    @DecimalMin(value = "0.00", inclusive = false, message = "El costo debe ser mayor a 0")
    @DecimalMax(value = "999999.99", message = "Costo muy alto")
    private BigDecimal costDest;
}
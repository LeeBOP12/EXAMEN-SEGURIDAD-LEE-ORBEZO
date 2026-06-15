package orbezo.ms_cliente.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequestDTO {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede exceder 50 caracteres")
    private String nomCli;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 50, message = "El apellido no puede exceder 50 caracteres")
    private String apeCli;

    @Min(value = 0, message = "La edad no puede ser negativa")
    @Max(value = 120, message = "Edad inválida")
    private Integer edadCli;

    @Pattern(regexp = "^[MF]$", message = "Sexo debe ser M o F")
    private String sexoCli;
}

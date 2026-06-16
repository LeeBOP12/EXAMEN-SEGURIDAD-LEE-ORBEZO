package orbezo.ms_reserva.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaRequestDTO {
    @NotNull(message = "La fecha de reserva es obligatoria")
    private LocalDate fechaReser;

    @NotNull(message = "La hora de reserva es obligatoria")
    private LocalTime horaReser;

    @NotNull(message = "El ID del cliente es obligatorio")
    @Min(value = 1, message = "ID de cliente inválido")
    private Long idCliente;

    @NotNull(message = "El ID de programación es obligatorio")
    @Min(value = 1, message = "ID de programación inválido")
    private Long idProgramacion;

    @NotNull(message = "El ID del destino es obligatorio")
    @Min(value = 1, message = "ID de destino inválido")
    private Long idDestino;
}
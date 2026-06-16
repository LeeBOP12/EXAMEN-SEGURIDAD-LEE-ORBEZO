package orbezo.ms_reserva.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaResponseDTO {
    private Long idReserva;
    private LocalDate fechaReser;
    private LocalTime horaReser;
    private Long idCliente;
    private Long idProgramacion;
    private Long idDestino;
}

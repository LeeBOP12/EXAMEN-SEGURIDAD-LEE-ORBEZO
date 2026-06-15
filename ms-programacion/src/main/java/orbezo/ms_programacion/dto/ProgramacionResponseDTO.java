package orbezo.ms_programacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgramacionResponseDTO {
    private Long idProgramacion;
    private Long idBus;
    private LocalDate fecha;
    private LocalTime hora;
}
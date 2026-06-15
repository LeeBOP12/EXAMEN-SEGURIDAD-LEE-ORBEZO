package orbezo.ms_asiento.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsientoResponseDTO {
    private Long idAsiento;
    private Long idBus;
    private String numAsi;
    private String estAsi;
}

package orbezo.ms_destino.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DestinoResponseDTO {
    private Long idDestino;
    private String ciuDest;
    private BigDecimal costDest;
}
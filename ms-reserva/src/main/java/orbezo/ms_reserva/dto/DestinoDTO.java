package orbezo.ms_reserva.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DestinoDTO {
    private Long idDestino;
    private String ciuDest;
    private BigDecimal costDest;
}
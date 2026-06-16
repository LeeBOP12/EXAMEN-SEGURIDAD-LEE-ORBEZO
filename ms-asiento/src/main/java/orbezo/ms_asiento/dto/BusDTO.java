package orbezo.ms_asiento.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusDTO {
    private Long idBus;
    private String modBus;
    private String placaBus;
    private Integer capBus;
}

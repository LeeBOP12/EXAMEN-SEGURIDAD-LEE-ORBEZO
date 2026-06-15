package orbezo.ms_bus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusResponseDTO {
    private Long idBus;
    private String modBus;
    private String placaBus;
    private Integer capBus;
}
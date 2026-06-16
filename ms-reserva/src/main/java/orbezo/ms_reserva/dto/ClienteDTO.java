package orbezo.ms_reserva.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private Long idCliente;
    private String nomCli;
    private String apeCli;
    private Integer edadCli;
    private String sexoCli;
}
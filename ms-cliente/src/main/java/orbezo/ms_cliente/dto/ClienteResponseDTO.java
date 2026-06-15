package orbezo.ms_cliente.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponseDTO {
    private Long idCliente;
    private String nomCli;
    private String apeCli;
    private Integer edadCli;
    private String sexoCli;
}
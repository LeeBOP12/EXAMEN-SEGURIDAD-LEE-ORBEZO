package orbezo.usuario_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {

    private Long idUsuario;
    private String username;
    private String email;
    private List<String> roles;
    private Boolean activo;
    private LocalDateTime fechaCreacion;
    private String password;
}

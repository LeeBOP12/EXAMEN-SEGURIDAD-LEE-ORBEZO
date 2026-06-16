package orbezo.usuario_service.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UsuarioResponseDTO {
    private Long idUsuario;
    private String username;
    private String email;
    private Set<String> roles;
    private Boolean activo;
    private LocalDateTime fechaCreacion;
    private String password;  // ✅ DEBE EXISTIR ESTE CAMPO

}
